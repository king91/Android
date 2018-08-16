package com.example.overseas_football.tap1_board.comment

import android.arch.lifecycle.MutableLiveData
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.base.BaseViewModel
import com.example.overseas_football.data.Resource
import com.example.overseas_football.model.BasicResModel
import com.example.overseas_football.model.CommentResModel
import com.example.overseas_football.tap1_board.CommunityApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentActivityVM : BaseViewModel() {
    val basicResLiveData = MutableLiveData<Resource<BasicResModel>>()
    val commentResLiveData = MutableLiveData<Resource<CommentResModel>>()
    fun getComments(num: String) {
        commentResLiveData.value = Resource.loading(null)
        CommunityApiManager().buildRetrofit()
                .getComment(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.result == "success") {
                        commentResLiveData.value = Resource.success(it)
                    }
                }, {
                    commentResLiveData.value = Resource.error(it)
                })
    }

    fun resisterComment(num: String, email: String, comment: String) {
        basicResLiveData.value = Resource.loading(null)
        CommunityApiManager().buildRetrofit()
                .resisterComment(num, email, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.result == "success") {
                        basicResLiveData.value = Resource.success(it)
                    }
                }, {
                    basicResLiveData.value = Resource.error(it)
                })
    }

    fun updateComment() {
        basicResLiveData.value = Resource.loading(null)

    }

    fun removeComment(c_index: String) {
        basicResLiveData.value = Resource.loading(null)
        CommunityApiManager().buildRetrofit()
                .removeComment(c_index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.result == "success") {
                        basicResLiveData.value = Resource.success(it)
                    }
                }, {
                    basicResLiveData.value = Resource.error(it)
                })
    }
}