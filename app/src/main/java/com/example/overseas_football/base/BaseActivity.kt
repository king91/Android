package com.example.overseas_football.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.include_toolbar.*

open class BaseActivity : AppCompatActivity(), View.OnClickListener {

    open fun setToolbarTitle(title: String) {
        toolbar_txt_title.text = title
        toolbar_img_back.setOnClickListener(this)
    }

    open fun setToolbarBackground(color: Int) {
        toolbar.background = ContextCompat.getDrawable(this, color)
    }

    open fun setToolbarTitleTextColor(color: Int) {
        toolbar_txt_title.setTextColor(ContextCompat.getColor(this, color))
    }

    open fun setToolbarRightTextVisible() {
        toolbar_txt_right.visibility = View.VISIBLE
    }

    open fun setToolbarRightText(text: String) {
        toolbar_txt_right.text = text
    }

    open fun setToolbarRightTextColor(color: Int) {
        toolbar_txt_right.setTextColor(ContextCompat.getColor(this, color))
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                toolbar_img_back.id -> finish()
            }
        }
    }
}