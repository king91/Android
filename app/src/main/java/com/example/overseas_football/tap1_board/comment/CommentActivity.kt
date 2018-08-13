package com.example.overseas_football.tap1_board.comment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import com.example.overseas_football.model.Board
import com.example.overseas_football.tap1_board.BoardAdapter
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.tab1.view.*

class CommentActivity : BaseActivity(), View.OnClickListener {
    lateinit var board: Board
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
                edit_comment.text.toString().let {
                    if (it.isEmpty()) {
                        Toast.makeText(this, "댓글을 입력해주세요.", Toast.LENGTH_LONG).show()
                        return
                    } else {
                        viewModel.resisterComment(board.num.toString(), Shared().getUser(this)?.email
                                ?: "", it)
                        edit_comment.setText("")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        board = intent.getParcelableExtra("board")
        viewModel.getComments(board.num.toString())
    }

    private fun initView() {
        setToolbarTitle("댓글")
        tv_resisterComment.setOnClickListener(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = Adapter(this)

        subcribeUI()
    }

    private fun subcribeUI() {
        viewModel.commentResLiveData.observe(this, Observer {
            if (it != null) {
                when {
                    it.success -> {
                        progressbar.visibility = View.GONE
                        (recyclerview.adapter as Adapter).addItems(it.getData()?.commentList
                                ?: emptyList())
                    }
                    it.loading -> {
                        progressbar.visibility = View.VISIBLE
                    }
                    it.error -> {
                        progressbar.visibility = View.GONE
                        showErrorToast(it.throwable?.message ?: "오류가 발생하였습니다.")
                    }
                }
            }
        })

        viewModel.basicResLiveData.observe(this, Observer {
            if (it != null) {
                when {
                    it.success -> {
                        progressbar.visibility = View.GONE
                    }
                    it.loading -> {
                        progressbar.visibility = View.VISIBLE
                    }
                    it.error -> {
                        progressbar.visibility = View.GONE
                        showErrorToast(it.throwable?.message ?: "오류가 발생하였습니다.")
                    }
                }
            }
        })
    }
}
