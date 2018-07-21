package com.example.overseas_football.login

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.overseas_football.R
import com.example.overseas_football.model.User
import com.example.overseas_football.network.Constants
import com.example.overseas_football.network.RetrofitClient
import com.example.overseas_football.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class LoginViewModel : BaseViewModel() {
    val userdata = MutableLiveData<User>()
    fun GoogleLogin() {
        val user = FirebaseAuth.getInstance().currentUser
        if (null == user) {

        } else {
            RetrofitClient()
                    .setRetrofit(Constants.BASE_URL)
                    .setResister(user.email!!, user.displayName!!, "google")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.result == "success") {
                            userdata.value = it.User
                        }
                    }
        }
    }

    fun GetGoogleSignInClient(context: Context): GoogleSignInClient {
        return GoogleSignIn.getClient(context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build())
    }


    fun requestKakaoAuth() {
        val keys = ArrayList<String>()
        keys.add("properties.nickname")
        keys.add("properties.profile_image")
        keys.add("kakao_account.email")

        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=" + errorResult!!
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
            }

            override fun onSuccess(response: MeV2Response) {
                Log.e("user nickname : ", "" + response.nickname)
                Log.e("email: ", "" + response.kakaoAccount.email)
                //카카오 이메일 Null 인경우 예외처리 필요함
                RetrofitClient()
                        .setRetrofit(Constants.BASE_URL)
                        .setResister(response.kakaoAccount.email, response.nickname, "kakao")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (it.result == "success") {
                                userdata.value = it.User
                            }
                        }
            }
        })
    }
}