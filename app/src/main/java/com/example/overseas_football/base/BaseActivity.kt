package com.example.overseas_football.base

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.include_toolbar.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    open fun getView(resources: Int) = LayoutInflater.from(this).inflate(resources, null)

    override fun setContentView(view: View?) {
        super.setContentView(view)
    }

    open fun setToolbarTile(activity: Activity, title: String) {
        activity.toolbar_txt_title.text = title
    }

}