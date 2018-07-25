package com.example.overseas_football.tap1_board

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.overseas_football.R
import com.example.overseas_football.databinding.Tab1Binding
import com.example.overseas_football.base.BaseFragment
import com.example.overseas_football.login.LoginActivity
import kotlinx.android.synthetic.main.tab1.*
import kotlinx.android.synthetic.main.tab1.view.*

class Tab1_Community : BaseFragment() {
    private val viewmodel: Tab1ViewModel by lazy {
        ViewModelProviders.of(this@Tab1_Community).get(Tab1ViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (DataBindingUtil.inflate(inflater, R.layout.tab1, container, false) as Tab1Binding).run {
            root.btn_login_activity.setOnClickListener {
                startActivity(requireActivity(), LoginActivity::class.java)
            }
            root.floating_btn_write.setOnClickListener {
                floating_menu_button.removeAllMenuButtons()
                startActivity(requireActivity(), WriteActivity::class.java)
            }
            setLifecycleOwner(this@Tab1_Community)
            tab1ViewModel = viewmodel
            viewmodel.getBoard(5)
            return root
        }
    }

    override fun onResume() {
        loginOrBeloginView(requireContext(), linear_belogin, floating_menu_button)
        super.onResume()
    }
}
