package com.example.overseas_football.di

import android.arch.lifecycle.ViewModel
import com.example.overseas_football.model.NewsResModel
import com.example.overseas_football.network.ApiManager
import com.example.overseas_football.network.RetrofitService
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DiModule(private val apiManager: ApiManager) : ViewModel() {
    fun getNews(country: String, categoty: String, apiKey: String): Observable<NewsResModel> = apiManager.getNews(country, categoty, apiKey)
}



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