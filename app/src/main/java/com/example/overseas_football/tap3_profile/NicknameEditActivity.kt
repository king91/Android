package com.example.overseas_football.tap3_profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.activity_nickname_edit.*

class NicknameEditActivity : BaseActivity() {
    private val nicknameEditVM by lazy { ViewModelProviders.of(this).get(NicknameEditVM::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname_edit)
        initView()
        tv_edit.setOnClickListener {
            nicknameEditVM.updateNickname(Shared().getUser(this)?.email
                    ?: "", edit_nickname.text.toString())
        }
    }

    private fun initView() {
        setToolbarTitle("닉네임 변경")
        edit_nickname.setText(intent.getStringExtra("nickname"))
        subscribeUI()
    }

    private fun subscribeUI() {
        nicknameEditVM.basicResLiveData.observe(this, Observer {
            if (it != null) {
                when {
                    it.error -> progressbar.visibility = View.GONE
                    it.loading -> progressbar.visibility = View.VISIBLE
                    it.success -> {
                        progressbar.visibility = View.GONE
                        val user = Shared().getUser(this)
                        user?.nickname = edit_nickname.text.toString()
                        Shared().saveUser(this, user)
                        finish()
                    }
                }
            }
        })
    }
}

