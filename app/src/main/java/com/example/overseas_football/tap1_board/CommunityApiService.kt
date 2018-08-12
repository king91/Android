package com.example.overseas_football.tap1_board

import com.example.overseas_football.model.BasicResModel
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CommunityApiService {
    @Multipart
    @POST("resister/content")
    fun resisterContent(@Part profileImage: MultipartBody.Part,
                        @Part("email") email: RequestBody,
                        @Part("nickname") nickname: RequestBody,
                        @Part("content") content: RequestBody): Single<BasicResModel>

    @FormUrlEncoded
    @POST("resister/content")
    fun resisterContent(@Field("email") email: String,
                        @Field("nickname") nickname: String,
                        @Field("content") content: String): Single<BasicResModel>

    @Multipart
    @POST("update/content")
    fun updateContent(@Part profileImage: MultipartBody.Part,
                      @Part("num") num: RequestBody,
                      @Part("content") content: RequestBody,
                      @Part("type") type: RequestBody): Single<BasicResModel>

    @FormUrlEncoded
    @POST("update/content")
    fun updateContent(@Field("num") num: String,
                      @Field("content") content: String,
                      @Field("type") type: String): Single<BasicResModel>
}