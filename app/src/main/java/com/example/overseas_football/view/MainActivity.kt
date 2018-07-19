package com.example.overseas_football.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.example.overseas_football.R
import com.example.overseas_football.databinding.ActivityMainBinding
import com.example.overseas_football.view.fragment.Tab1_Community
import com.example.overseas_football.view.fragment.Tab2_News
import com.example.overseas_football.view.fragment.Tab3_MyProfile
import com.example.overseas_football.viewmodel.MainViewModel


class MainActivity : BaseActivity() {
    val mainViewModel = MainViewModel()

    val tab1_Community = Tab1_Community()
    val tab2_News = Tab2_News()
    val tab3_MyProfile = Tab3_MyProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.mainActivity = mainViewModel
        binding.bottombar.setOnTabSelectListener { tabId ->
            val transaction = supportFragmentManager.beginTransaction()
            when (tabId) {
                R.id.tab1_community -> transaction.replace(R.id.framelayout, tab1_Community as Fragment).commit()
                R.id.tab2_news -> transaction.replace(R.id.framelayout, tab2_News).commit()
                R.id.tab3_myprofile -> transaction.replace(R.id.framelayout, tab3_MyProfile).commit()
            }
        }
    }
}
