package com.example.overseas_football.network

import com.example.overseas_football.model.BasicResModel
import com.example.overseas_football.model.BoardResModel
import com.example.overseas_football.model.NewsResModel
import com.example.overseas_football.model.UserLoginResModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RetrofitService {
    @FormUrlEncoded
    @POST("/signup")
    fun setResister(@Field("email") email: String,
                    @Field("nickname") nickname: String,
                    @Field("division") division: String): Observable<UserLoginResModel>

    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country: String,
                @Query("category") categoty: String,
                @Query("apiKey") apiKey: String): Observable<NewsResModel>

    @GET("/board")
    fun getBoard(@Query("limit") limit: Int,
                 @Query("size") size: Int): Observable<BoardResModel>

    @Multipart
    @POST("/profileImage")
    fun setProfileImage(@Part profileImage: MultipartBody.Part,
                        @Part("email") email: RequestBody): Observable<BasicResModel>

    @FormUrlEncoded
    @POST("/editNickname")
    fun updateNickname(@Field("email") email: String,
                       @Field("edit_nickname") edit_nickname: String): Observable<BasicResModel>
}

