package com.example.overseas_football.tap1_board

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import com.example.overseas_football.model.Board
import com.example.overseas_football.model.User
import com.example.overseas_football.utill.Shared
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.include_toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class WriteActivity : BaseActivity() {
    private val viewModel: WriteActivityVM by lazy {
        ViewModelProviders.of(this@WriteActivity).get(WriteActivityVM::class.java)
    }
    private var resultCropImage: Uri? = null
    private var board: Board? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        initView()

        toolbar_txt_right.setOnClickListener {
            if (edit_content.text.toString().isEmpty()) {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                with(Shared().getUser(this)) {
                    if (this != null) {
                        when (toolbar_txt_right.text.toString()) {
                            "등록" -> {
                                if (resultCropImage == null) {
                                    viewModel.resisterContent(email, nickname, edit_content.text.toString())
                                } else {
                                    upload(false, resultCropImage!!, this)
                                }
                            }
                            "수정" -> {
                                when (resultCropImage) {
                                    null -> viewModel.updateContent(board!!.num.toString(), edit_content.text.toString(),"1")
                                    Uri.parse(BuildConfig.BASE_URL + "glideBoard?img=" + board!!.b_img) -> {
                                        viewModel.updateContent(board!!.num.toString(), edit_content.text.toString(),"0")
                                    }
                                    else-> upload(true, resultCropImage!!, this, board!!.num.toString())
                                }
                            }
                        }
                    }
                }
            }
        }

        floating_btn_camera.setOnClickListener {
            floating_menu_button.removeAllMenuButtons()
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
        }

        txt_img_remove.setOnClickListener {
            framelayout.visibility = View.GONE
            resultCropImage = null
        }
        viewModel.responseData.observe(this, Observer {
            if (it != null) {
                when {
                    it.loading -> progress_bar.visibility = View.VISIBLE
                    it.error -> {
                        progress_bar.visibility = View.VISIBLE
                        Log.e("error:", it.throwable.toString())
                    }
                    it.success -> {
                        progress_bar.visibility = View.GONE
                        finish()
                    }
                }
            }

        })
    }

    private fun initView() {
        setToolbarTitle("글 작성")
        setToolbarBackground(R.color.blue_tab1)
        setToolbarTitleTextColor(R.color.white)
        setToolbarRightTextVisible()
        setToolbarRightText("등록")
        setToolbarRightTextColor(R.color.white)

        board = intent.getParcelableExtra("board")
        if (board != null) {
            with(board) {
                edit_content.setText(this!!.b_content)
                setToolbarTitle("글 수정")
                setToolbarRightText("수정")
                b_img.let {
                    if (!it.isNullOrEmpty()) {
                        resultCropImage = Uri.parse(BuildConfig.BASE_URL + "glideBoard?img=" + it)
                        Glide.with(this@WriteActivity)
                                .load(BuildConfig.BASE_URL + "glideBoard?img=" + it)
                                .into(object : SimpleTarget<Drawable>() {
                                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                        framelayout.visibility = View.VISIBLE
                                        imgview.setImageDrawable(resource)
                                        imgview.requestLayout()
                                    }
                                })
                    }
                }
            }
        }
    }

    private fun upload(isUpdate: Boolean, uri: Uri, user: User, num: String? = "") {
        with(user) {
            val file = File(uri.path)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val fileBody = MultipartBody.Part.createFormData("img", file.name, requestFile)
            val requestEmail = RequestBody.create(MultipartBody.FORM, email)
            val requestContent = RequestBody.create(MultipartBody.FORM, edit_content.text.toString())
            if (isUpdate) {
                val requestNum = RequestBody.create(MultipartBody.FORM, num!!)
                val requestType = RequestBody.create(MultipartBody.FORM, "1")
                viewModel.updateContent(fileBody, requestNum, requestContent,requestType)
            } else {
                val requestNickname = RequestBody.create(MultipartBody.FORM, nickname)
                viewModel.resisterContent(fileBody, requestEmail, requestNickname, requestContent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            resultCropImage = CropImage.getActivityResult(data).uri
            if (resultCode == Activity.RESULT_OK) {
                framelayout.visibility = View.VISIBLE
                Glide.with(this).load(resultCropImage).into(imgview)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = (resultCropImage as CropImage.ActivityResult).error
            }
        }
    }
}
