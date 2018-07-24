package com.example.overseas_football.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiManager @Inject constructor(private val overseasApi: OverseasApi) {

    fun getNews(country: String, categoty: String, apiKey: String) {
        overseasApi
                .getNews(country, categoty, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}