package com.example.overseas_football.tap1_board

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import com.example.overseas_football.utill.Shared
import com.theartofdev.edmodo.cropper.CropImage
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        initView()
        toolbar_txt_right.setOnClickListener {
            if (edit_content.text.toString().isEmpty()) {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                with(Shared().getUser(this)) {
                    if (this != null) {
                        viewModel.resisterContent(email, nickname, edit_content.text.toString())
                    }
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val file = File(result.uri.path)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val fileBody = MultipartBody.Part.createFormData("img", file.name, requestFile)
                with(Shared().getUser(this)) {
                    if (this != null) {
                        val requestEmail = RequestBody.create(MultipartBody.FORM, email)
                        val requestNickname = RequestBody.create(MultipartBody.FORM, nickname)
                        val requestContent = RequestBody.create(MultipartBody.FORM, edit_content.text.toString())
                        viewModel.resisterContent(fileBody, requestEmail, requestNickname, requestContent)
                    }
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}
