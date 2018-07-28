package com.example.overseas_football.tap1_board

import android.arch.lifecycle.MutableLiveData
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.data.Resource
import com.example.overseas_football.model.BoardResModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Tab1ViewModel : BaseViewModel() {
    val boardLiveData = MutableLiveData<Resource<BoardResModel>>()
    fun getBoard(limit: Int, size: Int) {
        boardLiveData.value = Resource.loading(null)
        setRetrofit(BuildConfig.BASE_URL)
                .getBoard(limit, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.boardList != null) {
                        it.isMoreData = true
                        boardLiveData.value = Resource.success(it)
                    } else {
                        it.isMoreData = false
                        boardLiveData.value = Resource.success(it)
                    }
                }, {
                    boardLiveData.value = Resource.error(it)
                })
    }
}