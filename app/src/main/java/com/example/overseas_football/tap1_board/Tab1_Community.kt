package com.example.overseas_football.tap1_board

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseFragment
import com.example.overseas_football.databinding.Tab1Binding
import com.example.overseas_football.login.LoginActivity
import com.example.overseas_football.model.Board
import com.example.overseas_football.model.Comment
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.tab1.*
import kotlinx.android.synthetic.main.tab1.view.*

class Tab1_Community : BaseFragment(), BoardAdapter.RecyclerviewPositionListener {

    private val viewmodel: Tab1ViewModel by lazy {
        ViewModelProviders.of(this@Tab1_Community).get(Tab1ViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (DataBindingUtil.inflate(inflater, R.layout.tab1, container, false) as Tab1Binding).run {
            root.btn_login_activity.setOnClickListener {
                startActivity(requireActivity(), LoginActivity::class.java)
            }
            root.floating_btn_write.setOnClickListener {
                startActivityForResult(Intent(requireActivity(), WriteActivity::class.java), 0)
            }

            setLifecycleOwner(this@Tab1_Community)
            tab1ViewModel = viewmodel
            initView(root)
            return root
        }
    }

    override fun lastPosition(position: Int) {
        viewmodel.getBoard(position + 1, Shared().getUser(requireContext())?.email ?: "")
    }

    override fun like(num: Int) {
        viewmodel.like(num, Shared().getUser(requireContext())?.email ?: "")
    }

    override fun cancelLike(num: Int) {
        viewmodel.cancelLike(num, Shared().getUser(requireContext())?.email ?: "")
    }

    override fun removeItem(num: Int, position: Int, board: Board) {
        viewmodel.removeBoard(num, board)
        (recyclerview.adapter as BoardAdapter).itemList.remove(board)
        (recyclerview.adapter as BoardAdapter).notifyItemRemoved(position)
        (recyclerview.adapter as BoardAdapter).notifyItemRangeChanged(position, (recyclerview.adapter as BoardAdapter).itemList.size)
    }

    private fun initView(root: View) {
        if (Shared().getUser(requireContext()) != null) {
            viewmodel.getBoard(0, Shared().getUser(requireContext())?.email ?: "")
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        root.recyclerview.layoutManager = layoutManager
        root.recyclerview.adapter = BoardAdapter(requireActivity(), this)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewmodel.boardLiveData.observe(this, Observer {
            if (it != null) {
                when {
                    it.success -> {
                        it.getData().let {
                            if (it != null) {
                                (recyclerview.adapter as BoardAdapter).addItem(it.boardList
                                        ?: emptyList(), it.isMoreData ?: false)
                            }
                        }
                        lotie_loading.visibility = View.GONE
                        lotie_loading.cancelAnimation()
                    }
                    it.loading -> {
                        lotie_loading.visibility = View.VISIBLE
                        lotie_loading.playAnimation()
                    }
                    it.error -> {
                        lotie_loading.visibility = View.GONE
                        lotie_loading.cancelAnimation()
                        showErrorToast(it.throwable?.message ?: "오류가 발생하였습니다.")
                    }
                }
            }
        })

        viewmodel.basicResModelLiveData.observe(this, Observer {
            if (it != null) {
                when {
                    it.success -> {
                        it.getData().let {
                            if (it != null && it.result == "success") {
                                lotie_loading.visibility = View.GONE
                                lotie_loading.cancelAnimation()
                            }
                        }
                    }
                    it.loading -> {
                        lotie_loading.visibility = View.VISIBLE
                        lotie_loading.playAnimation()
                    }
                    it.error -> {
                        lotie_loading.visibility = View.GONE
                        lotie_loading.cancelAnimation()
                        showErrorToast(it.throwable?.message ?: "오류가 발생하였습니다.")
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loginOrBeloginView(requireContext(), linear_belogin, floating_menu_button, const_board)
        Shared().getUser(requireContext()).let {
            if (it != null) {
                if (arguments != null) {
                    val num = arguments!!.getInt("num", 0)
                    val size = arguments!!.getInt("size", 0)

                    val itemList = (recyclerview.adapter as BoardAdapter).itemList
                    for (board in itemList) {
                        if (num == board.num) {
                            val index = itemList.indexOf(board)
                            itemList[index].b_comment = size.toString()
//                            (recyclerview.adapter as BoardAdapter).notifyItemRangeChanged()
                            recyclerview.scrollToPosition((recyclerview.adapter as BoardAdapter).itemList.size-1)
                        }
                    }
                }
            }
        }

    }
}
