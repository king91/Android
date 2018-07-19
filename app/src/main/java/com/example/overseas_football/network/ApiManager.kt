package com.example.overseas_football.network

import com.example.overseas_football.model.BasicResModel
import com.example.overseas_football.model.NewsResModel
import com.example.overseas_football.model.UserLoginResModel
import com.example.overseas_football.network.OverseasApi
import com.example.overseas_football.network.RetrofitService
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiManager(val overseasApi: OverseasApi) : RetrofitService {
    override fun setResister(email: String, nickname: String, division: String): Observable<UserLoginResModel> = overseasApi.setResister(email, nickname, division)

    override fun getNews(country: String, categoty: String, apiKey: String): Observable<NewsResModel> = overseasApi.getNews(country, categoty, apiKey)

    override fun setProfileImage(profileImage: MultipartBody.Part, email: RequestBody): Observable<BasicResModel> = overseasApi.setProfileImage(profileImage, email)
}