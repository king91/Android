package com.example.overseas_football.model

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable


data class UserLoginResModel(
    val result: String,
    val User: User
)

data class User(
        val email: String,
        var nickname: String,
        var img: String?=null,
        val division: String
): Parcelable, BaseObservable() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(nickname)
        parcel.writeString(img)
        parcel.writeString(division)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

