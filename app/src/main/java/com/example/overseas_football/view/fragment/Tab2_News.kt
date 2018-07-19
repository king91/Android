package com.example.overseas_football.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.R
import com.example.overseas_football.databinding.Tab2Binding
import com.example.overseas_football.di.Presenter
import com.example.overseas_football.view.BaseFragment
import com.example.overseas_football.view.adapter.NewsAdapter
import com.example.overseas_football.viewmodel.Tab2ViewModel
import kotlinx.android.synthetic.main.tab2.*
import kotlinx.android.synthetic.main.tab2.view.*
import org.koin.android.ext.android.inject

class Tab2_News : BaseFragment() {
    private val tab2ViewModel: Tab2ViewModel by lazy {
        ViewModelProviders
                .of(this@Tab2_News)
                .get(Tab2ViewModel::class.java)
    }
    private val apiManager: Presenter by inject()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (DataBindingUtil.inflate(inflater, R.layout.tab2, container, false) as Tab2Binding).let {
            with(it) {
                tab2viewmodel = tab2ViewModel
                root.swipelayout.isRefreshing = true
                tab2ViewModel.getNews(BuildConfig.NEWS_API_KEY)
                root.swipelayout.setOnRefreshListener {
                    tab2ViewModel.getNews(BuildConfig.NEWS_API_KEY)
                }
                observerNews()
                return root
            }
        }
    }

    private fun observerNews() {
        tab2ViewModel.newsList.observe(this, Observer {
            swipelayout.isRefreshing = false
            if (it != null) {
                news_recyclerview.layoutManager = LinearLayoutManager(context)
                news_recyclerview.adapter = NewsAdapter(requireContext(), it)
            } else {

            }
        })

    }
}