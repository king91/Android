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
                .subscribe({
                    if (it.result == "success") {
                        responseData.value = Resource.success(it)
                    }
                }, {
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
                .subscribe({
                    if (it.result == "success") {
                        responseData.value = Resource.success(it)
                    }
                }, {
                    responseData.value = Resource.error(it)
                })
    }

    fun updateContent(img: MultipartBody.Part, num: RequestBody, content: RequestBody, type: RequestBody) {
        responseData.value = Resource.loading(null)
        CommunityApiManager()
                .buildRetrofit()
                .updateContent(img, num, content, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.result == "success") {
                        responseData.value = Resource.success(it)
                    }
                }, {
                    responseData.value = Resource.error(it)
                })
    }

    fun updateContent(num: String, content: String, type: String) {
        responseData.value = Resource.loading(null)
        CommunityApiManager()
                .buildRetrofit()
                .updateContent(num, content, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.result == "success") {
                        responseData.value = Resource.success(it)
                    }
                }, {
                    responseData.value = Resource.error(it)
                })
    }

}