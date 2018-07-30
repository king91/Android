package com.example.overseas_football.tap3_profile

import android.arch.lifecycle.MutableLiveData
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.data.Resource
import com.example.overseas_football.model.BasicResModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NicknameEditVM : BaseViewModel() {
    val basicResLiveData = MutableLiveData<Resource<BasicResModel>>()
    fun updateNickname(email: String, nickname: String) {
        basicResLiveData.value = Resource.loading(null)
        setRetrofit(BuildConfig.BASE_URL)
                .updateNickname(email, nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.result == "success") {
                        basicResLiveData.value = Resource.success(it)
                    }
                }, {
                    basicResLiveData.value = Resource.error(it)
                })
    }
}