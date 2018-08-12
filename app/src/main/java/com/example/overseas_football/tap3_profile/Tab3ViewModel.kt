package com.example.overseas_football.tap3_profile

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.overseas_football.model.BasicResModel
import com.example.overseas_football.network.Constants
import com.example.overseas_football.network.RetrofitClient
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.data.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Tab3ViewModel : BaseViewModel() {
    val basicResModel: MutableLiveData<Resource<BasicResModel>> = MutableLiveData()

    fun setProfileImage(imagefile: MultipartBody.Part, email: RequestBody) {
        basicResModel.value = Resource.loading(null)
        RetrofitClient()
                .setRetrofit(Constants.BASE_URL)
                .setProfileImage(imagefile, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.result == "success") {
                                basicResModel.value = Resource.success(it)
                            }
                        },
                        {
                            basicResModel.value = Resource.error(it)
                        }
                )
    }
}