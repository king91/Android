package com.example.overseas_football.network

import com.example.overseas_football.model.BasicResModel
import com.example.overseas_football.model.BoardResModel
import com.example.overseas_football.model.NewsResModel
import com.example.overseas_football.model.UserLoginResModel
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RetrofitService {
    @FormUrlEncoded
    @POST("/signup")
    fun setResister(@Field("email") email: String,
                    @Field("nickname") nickname: String,
                    @Field("division") division: String): Single<UserLoginResModel>

    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country: String,
                @Query("category") categoty: String,
                @Query("apiKey") apiKey: String): Single<NewsResModel>

    @GET("/board")
    fun getBoard(@Query("limit") limit: Int,
                 @Query("email") email: String): Single<BoardResModel>

    @Multipart
    @POST("/profileImage")
    fun setProfileImage(@Part profileImage: MultipartBody.Part,
                        @Part("email") email: RequestBody): Single<BasicResModel>

    @FormUrlEncoded
    @POST("/editNickname")
    fun updateNickname(@Field("email") email: String,
                       @Field("edit_nickname") edit_nickname: String): Single<BasicResModel>

    @FormUrlEncoded
    @POST("/like")
    fun like(@Field("num") num: Int,
             @Field("req_email") reqEmail: String): Single<BasicResModel>

    @FormUrlEncoded
    @POST("/cancelLike")
    fun cancelLike(@Field("num") num: Int,
                   @Field("req_email") reqEmail: String): Single<BasicResModel>

    @FormUrlEncoded
    @POST("/board/remove")
    fun removeBoard(@Field("num") num: Int): Single<BasicResModel>
}

