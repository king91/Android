package com.example.overseas_football.tap3_profile

import android.os.Bundle
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import kotlinx.android.synthetic.main.activity_nickname_edit.*

class NicknameEditActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname_edit)
        edit_nickname.setText(intent.getStringExtra("nickname"))
    }
}
