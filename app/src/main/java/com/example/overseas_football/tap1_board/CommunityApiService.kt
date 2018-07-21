package com.example.overseas_football.tap1_board

import android.provider.ContactsContract
import com.example.overseas_football.model.BasicResModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CommunityApiService {
    @Multipart
    @POST("resister/content")
    fun resisterContent(@Part profileImage: MultipartBody.Part,
                        @Part("email") email: RequestBody,
                        @Part("nickname") nickname: RequestBody,
                        @Part("content") content: RequestBody): Observable<BasicResModel>

    @FormUrlEncoded
    @POST("resister/content")
    fun resisterContent(@Field("email") email: String,
                        @Field("nickname") nickname: String,
                        @Field("content") content: String): Observable<BasicResModel>
}