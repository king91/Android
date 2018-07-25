package com.example.overseas_football.tap1_board

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.data.Resource
import com.example.overseas_football.model.BasicResModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class WriteActivityVM : BaseViewModel() {
    val responseData = MutableLiveData<Resource<BasicResModel>>()
    fun resisterContent(img: MultipartBody.Part, email: RequestBody, nickname: RequestBody, content: RequestBody) {
        responseData.value = Resource.loading(null)
        CommunityApiManager()
                .buildRetrofit()
                .resisterContent(img, email, nickname, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    if (it.result == "success") {
                        responseData.value = Resource.success(it)
                    }
                }, onError = {
                    responseData.value = Resource.error(it)
                })
    }


    fun resisterContent(email: String, nickname: String, content: String) {
        responseData.value = Resource.loading(null)
        CommunityApiManager()
                .buildRetrofit()
                .resisterContent(email, nickname, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    responseData.value = Resource.success(it)
                }, onError = {
                    responseData.value = Resource.error(it)
                })
    }
}