package com.example.overseas_football.tap1_board

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : BaseActivity(), View.OnClickListener {
    val viewModel: CommentActivityVM by lazy { ViewModelProviders.of(this).get(CommentActivityVM::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        initView()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            tv_resisterComment -> {

            }
        }
    }

    private fun initView() {
        setToolbarTitle("댓글")
        tv_resisterComment.setOnClickListener(this)
    }


}
