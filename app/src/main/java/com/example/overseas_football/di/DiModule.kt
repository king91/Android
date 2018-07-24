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

//class DiModule(private val apiManager: ApiManager) : ViewModel() {
//    fun getNews(country: String, categoty: String, apiKey: String): Observable<NewsResModel> = apiManager.getNews(country, categoty, apiKey)
//}