package com.example.overseas_football.tap2_news

import android.arch.lifecycle.MutableLiveData
import com.example.overseas_football.model.Articles
import com.example.overseas_football.network.Constants
import com.example.overseas_football.network.RetrofitClient
import com.example.overseas_football.base.BaseViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray

class Tab2ViewModel : BaseViewModel() {

    val newsList = MutableLiveData<ArrayList<Articles>>()
    fun getNews(news_apikey: String) {
        RetrofitClient()
                .setRetrofit(Constants.NEWS_URL)
                .getNews("kr", "sports", news_apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.totalResults == 20) {
                        val jsonArray = JSONArray(Gson().toJson(it.articles))
                        val contentsList: ArrayList<Articles> = ArrayList()
                        for (x in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(x)
                            val author = jsonObject.optString("author", "null")
                            val title = jsonObject.optString("title", "null")
                            val description = jsonObject.optString("description", "null")
                            val url = jsonObject.optString("url", "null")
                            val urlToImage = jsonObject.optString("urlToImage", "null")
                            val publishedAt = jsonObject.optString("publishedAt", "null")
                            contentsList.add(Articles(author, title, description, url, urlToImage, publishedAt, null))
                        }
                        newsList.value = contentsList
                    }
                }
    }
}
