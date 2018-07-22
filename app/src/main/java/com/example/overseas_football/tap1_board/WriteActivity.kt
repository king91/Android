package com.example.overseas_football.tap1_board

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
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
    private var resultCropImage: CropImage.ActivityResult? = null
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
                        if (resultCropImage == null) {
                            viewModel.resisterContent(email, nickname, edit_content.text.toString())
                        } else {
                            upload(resultCropImage as CropImage.ActivityResult, this)
                        }
                    }
                }
            }
        }

        floating_btn_camera.setOnClickListener {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
        }

        txt_img_remove.setOnClickListener {
            framelayout.visibility= View.GONE
            resultCropImage=null
        }
    }

    private fun initView() {
        setToolbarTitle("글 작성")
        setToolbarBackground(R.color.blue_tab1)
        setToolbarTitleTextColor(R.color.white)
        setToolbarRightTextVisible()
        setToolbarRightText("등록")
        setToolbarRightTextColor(R.color.white)
    }

    private fun upload(result: CropImage.ActivityResult, user: User) {
        val file = File(result.uri.path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val fileBody = MultipartBody.Part.createFormData("img", file.name, requestFile)
        with(user) {
            val requestEmail = RequestBody.create(MultipartBody.FORM, email)
            val requestNickname = RequestBody.create(MultipartBody.FORM, nickname)
            val requestContent = RequestBody.create(MultipartBody.FORM, edit_content.text.toString())
            viewModel.resisterContent(fileBody, requestEmail, requestNickname, requestContent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            resultCropImage = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                framelayout.visibility= View.VISIBLE
                Glide.with(this).load((resultCropImage as CropImage.ActivityResult).uri).into(imgview)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = (resultCropImage as CropImage.ActivityResult).error
            }
        }
    }
}
