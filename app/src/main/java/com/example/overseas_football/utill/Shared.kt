package com.example.overseas_football.utill

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.overseas_football.model.User
import com.google.gson.Gson


class Shared {
    fun saveUser(context: Context, user: User?) {
        val pref = context.getSharedPreferences("user", MODE_PRIVATE).edit()
        pref.putString("user", Gson().toJson(user))
        pref.apply()
    }

    fun getUser(context: Context): User? {
        val pref = context.getSharedPreferences("user", MODE_PRIVATE)
        val result = pref.getString("user", null)
        return if (result != null) {
            Gson().fromJson(result, User::class.java)
        } else {
            null
        }
    }

    fun removeUser(context: Context) {
        val pref = context.getSharedPreferences("user", MODE_PRIVATE).edit()
        pref.remove("user")
        pref.apply()
    }
}

