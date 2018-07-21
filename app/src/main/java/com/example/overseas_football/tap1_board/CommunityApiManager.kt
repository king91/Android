package com.example.overseas_football.tap1_board

import com.example.overseas_football.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CommunityApiManager {

    fun buildRetrofit(): CommunityApiService {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()
        val retrofitApi = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofitApi.create(CommunityApiService::class.java)
    }
}