package com.example.overseas_football.tap1_board

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.overseas_football.R
import com.example.overseas_football.databinding.Tab1Binding
import com.example.overseas_football.base.BaseFragment
import com.example.overseas_football.login.LoginActivity
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
                startActivity(requireActivity(), WriteActivity::class.java)
            }

            setLifecycleOwner(this@Tab1_Community)
            tab1ViewModel = viewmodel
            initView(root)
            return root
        }
    }

    override fun lastPosition(position: Int) {
        viewmodel.getBoard(position+1,(recyclerview.adapter as BoardAdapter).itemCount)
    }

    private fun initView(root: View) {
        if (Shared().getUser(requireContext()) != null) {
            viewmodel.getBoard(0,0)
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        root.recyclerview.layoutManager = layoutManager
        root.recyclerview.adapter = BoardAdapter(requireActivity(),this)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewmodel.boardLiveData.observe(this, Observer {
            if (it != null) {
                when {
                    it.success -> {
                        it.getData().let {
                            if (it != null) {
                                (recyclerview.adapter as BoardAdapter).addItem(it.boardList?: emptyList(),it.isMoreData?:false)
                            }
                        }
                        lotie_loading.visibility=View.GONE
                        lotie_loading.cancelAnimation()
                    }
                    it.loading -> {
                        lotie_loading.visibility=View.VISIBLE
                        lotie_loading.playAnimation()
                    }
                    it.error -> {
                        lotie_loading.visibility=View.GONE
                        lotie_loading.cancelAnimation()
                        showErrorToast(it.throwable?.message ?: "오류가 발생하였습니다.")
                    }
                }
            }
        })
    }

    override fun onResume() {
        loginOrBeloginView(requireContext(), linear_belogin, floating_menu_button, const_board)
        super.onResume()
    }
}
