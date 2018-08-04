package com.example.overseas_football.tap1_board

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity

class CommentActivity : BaseActivity() {
    val viewModel: CommentActivityVM by lazy { ViewModelProviders.of(this).get(CommentActivityVM::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        initView()
    }

    private fun initView() {
        setToolbarTitle("댓글")
    }


}
