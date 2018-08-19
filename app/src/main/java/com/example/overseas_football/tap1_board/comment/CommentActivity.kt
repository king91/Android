package com.example.overseas_football.tap1_board.comment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import com.example.overseas_football.model.Board
import com.example.overseas_football.model.Comment
import com.example.overseas_football.tap1_board.Tab1ViewModel
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.activity_comment.*


class CommentActivity : BaseActivity(), View.OnClickListener, Adapter.RecyclerviewPositionListener {

    lateinit var board: Board
    val viewModel: CommentActivityVM by lazy { ViewModelProviders.of(this).get(CommentActivityVM::class.java) }
    val boardViewModel: Tab1ViewModel by lazy { ViewModelProviders.of(this).get(Tab1ViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        initView()
        board = intent.getParcelableExtra("board")
        viewModel.getComments(board.num.toString())
    }

    override fun onBackPressed() {
        (recyclerview.adapter as Adapter).itemlist.let {
            if (it.size > 0) {
                val intent = Intent()
                intent.putExtra("num", board.num)
                intent.putExtra("size", (recyclerview.adapter as Adapter).itemlist.size)
                setResult(1, intent)
            }
        }
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            tv_resisterComment -> {
                edit_comment.text.toString().let {
                    Log.e("it", it.length.toString())
                    if (it.replace(" ", "").isEmpty()) {
                        Toast.makeText(this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        return
                    } else {
                        (recyclerview.adapter as Adapter).addItem(Comment())
                        viewModel.resisterComment(board.num.toString(), Shared().getUser(this)?.email
                                ?: "", it)
                        edit_comment.setText("")
                    }
                }
            }
        }
    }

    override fun commentItemUpdate(position: Int, comment: Comment) {
        viewModel.updateComment(comment.c_index, comment.c_comment)
    }

    override fun removeItem(c_index: Int, position: Int, comment: Comment) {
        viewModel.removeComment(c_index.toString())
        (recyclerview.adapter as Adapter).itemlist.remove(comment)
        (recyclerview.adapter as Adapter).notifyItemRangeRemoved(position, (recyclerview.adapter as Adapter).itemlist.size)
        recyclerview.scrollToPosition((recyclerview.adapter as Adapter).itemlist.size - 1)
    }

    private fun initView() {
        setToolbarTitle("댓글")
        tv_resisterComment.setOnClickListener(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = Adapter(this, this)

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
                        recyclerview.scrollToPosition((recyclerview.adapter as Adapter).itemlist.size - 1)
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
                        viewModel.getComments(board.num.toString())
                        (recyclerview.adapter as Adapter).notifyDataSetChanged()
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

