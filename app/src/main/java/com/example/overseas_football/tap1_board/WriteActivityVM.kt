package com.example.overseas_football.tap1_board

import android.util.Log
import com.example.overseas_football.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class WriteActivityVM : BaseViewModel() {
    fun resisterContent(img: MultipartBody.Part, email: RequestBody, nickname: RequestBody, content: RequestBody) {
        CommunityApiManager()
                .buildRetrofit()
                .resisterContent(img, email, nickname, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    if (it.result == "success") {

                    }
                }, onError = {
                    Log.e("확인22", it.message)
                })
    }


    fun resisterContent(email: String, nickname: String, content: String) {
        CommunityApiManager()
                .buildRetrofit()
                .resisterContent(email, nickname, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    Log.e("확인11", it.message)
                }, onError = {
                    Log.e("확인22", it.message)
                })
    }
}