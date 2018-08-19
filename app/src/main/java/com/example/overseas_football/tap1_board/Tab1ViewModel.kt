package com.example.overseas_football.tap1_board

import android.arch.lifecycle.MutableLiveData
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.data.Resource
import com.example.overseas_football.model.BasicResModel
import com.example.overseas_football.model.Board
import com.example.overseas_football.model.BoardResModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Tab1ViewModel : BaseViewModel() {
    val boardLiveData = MutableLiveData<Resource<BoardResModel>>()
    val basicResModelLiveData = MutableLiveData<Resource<BasicResModel>>()
    val commentCountLiveData = MutableLiveData<HashMap<Int, Int>>()

    fun getBoard(limit: Int, email: String) {
        boardLiveData.value = Resource.loading(null)
        setRetrofit(BuildConfig.BASE_URL)
                .getBoard(limit, email)
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

    fun removeBoard(num: Int, board: Board) {
        boardLiveData.value = Resource.loading(null)
        setRetrofit(BuildConfig.BASE_URL)
                .removeBoard(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    basicResModelLiveData.value = Resource.success(it)
                }, {
                    basicResModelLiveData.value = Resource.error(it)
                })
    }

    fun cancelLike(num: Int, email: String) {
        setRetrofit(BuildConfig.BASE_URL)
                .cancelLike(num, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    basicResModelLiveData.value = Resource.success(it)
                }, {
                    basicResModelLiveData.value = Resource.error(it)
                })
    }

    fun like(num: Int, email: String) {
        setRetrofit(BuildConfig.BASE_URL)
                .like(num, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    basicResModelLiveData.value = Resource.success(it)
                }, {
                    basicResModelLiveData.value = Resource.error(it)
                })
    }
}