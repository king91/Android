package com.example.overseas_football.view.fragment

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.overseas_football.R
import com.example.overseas_football.databinding.Tab3Binding
import com.example.overseas_football.network.Constants
import com.example.overseas_football.view.BaseFragment
import com.example.overseas_football.view.utill.Shared
import com.example.overseas_football.viewmodel.Tab3ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kakao.auth.Session
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.tab3.*
import kotlinx.android.synthetic.main.tab3.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class Tab3_MyProfile : BaseFragment() {
    private val viewmodel: Tab3ViewModel by lazy {
        ViewModelProviders.of(this@Tab3_MyProfile)
                .get(Tab3ViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (DataBindingUtil.inflate(inflater, R.layout.tab3, container, false) as Tab3Binding).run {
            setLifecycleOwner(this@Tab3_MyProfile)
            tab3ViewModel = viewmodel
            basicResModelObserver()

            root.circleimg_profile.setOnClickListener {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(requireContext(), this@Tab3_MyProfile)
            }
            root.btn_login_activity.setOnClickListener {
                startActivity(Intent(requireContext(), com.example.overseas_football.view.LoginActivity::class.java))
            }
            root.btn_logout.setOnClickListener {
                openBasicDialog(requireActivity(), "로그아웃", "로그아웃 하시겠습니까?")
                        .onPositive { dialog, which ->
                            if (FirebaseAuth.getInstance().currentUser != null) {
                                FirebaseAuth.getInstance().signOut()
                            }
                            if (Session.getCurrentSession().isOpened) {
                                UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                                    override fun onCompleteLogout() {
                                        Log.e("kakao", "logout")
                                    }

                                })
                            }
                            Shared().removeUser(requireActivity())
                            isLoginViewCheck(linearLayout_Login, linearLayout_beLogin, circleimg_profile)
                            Toast.makeText(activity, "정상적으로 로그인 되었습니다.", Toast.LENGTH_LONG).show()
                        }.show()
            }
            return root
        }
    }

    private fun basicResModelObserver() {
        viewmodel.basicResModel.observe(this, Observer {
            if (it!!.result == "success") {
                val user = Shared().getUser(requireContext())
                if (user != null) {
                    user.img = it.message
                    Shared().saveUser(requireContext(), user)
                    circleimg_profile.background = null
                    Glide.with(requireActivity())
                            .load(Constants.BASE_URL + "glideProfile?img=" + it.message)
                            .into(circleimg_profile)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        isLoginViewCheck(linearLayout_Login, linearLayout_beLogin, circleimg_profile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val file = File(result.uri.path)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val fileBody = MultipartBody.Part.createFormData("profileImage", file.name, requestFile)
                val requestEmail = RequestBody.create(MultipartBody.FORM, Shared().getUser(context!!)!!.email)
                viewmodel.setProfileImage(fileBody, requestEmail)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}