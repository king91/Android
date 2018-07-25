package com.example.overseas_football

import android.app.Application
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.network.RetrofitClient
import com.kakao.auth.*
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext

val module = applicationContext {
    viewModel { BaseViewModel() }
    bean { RetrofitClient().setRetrofit(BuildConfig.BASE_URL) }
}

class MyService : Application() {
    override fun onCreate() {
        super.onCreate()
//        startKoin(this, listOf(module))
//        DaggerAppComponent.builder().apiModule(ApiModule())
        KakaoSDK.init(KakaoSDKAdapter().SDKAdapter())
    }

    inner class KakaoSDKAdapter {

        inner class SDKAdapter : KakaoAdapter() {
            /**
             * Session Config에 대해서는 default값들이 존재한다.
             * 필요한 상황에서만 override해서 사용하면 됨.
             * @return Session의 설정값.
             */
            override fun getSessionConfig(): ISessionConfig {
                return object : ISessionConfig {
                    override fun isSaveFormData(): Boolean {
                        return true
                    }

                    override fun getAuthTypes(): Array<AuthType> {
                        return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                    }

                    override fun isSecureMode(): Boolean {
                        return false
                    }

                    override fun getApprovalType(): ApprovalType {
                        return ApprovalType.INDIVIDUAL
                    }

                    override fun isUsingWebviewTimer(): Boolean {
                        return false
                    }

                }
            }

            override fun getApplicationConfig(): IApplicationConfig {
                return IApplicationConfig { applicationContext }
            }
        }
    }
}
