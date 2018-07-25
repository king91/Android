package com.example.overseas_football.tap1_board

import android.util.Log
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Tab1ViewModel: BaseViewModel() {

    fun getBoard(limit:Int){
        setRetrofit(BuildConfig.BASE_URL)
                .getBoard(limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("22",it.toString())
                },{
                    Log.e("111",it.toString())
                })
    }
}