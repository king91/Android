package com.example.overseas_football.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.example.overseas_football.network.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

open class BaseViewModel:ViewModel(){

    fun setRetrofit(url: String): RetrofitService {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()

        val retrofitApi = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofitApi.create(RetrofitService::class.java)
    }

}