package com.example.overseas_football.model

import android.os.Parcel
import android.os.Parcelable


data class BoardResModel(
        val result: String,
        val boardList: List<Board>? = emptyList(),
        var isMoreData: Boolean? = false
)

data class Board(
        val num: Int,
        val b_email: String,
        val b_content: String,
        val b_time: String,
        val nickname: String,
        var islike: String,
        val b_like: Int,
        val b_img: String? = "",
        val img: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(num)
            writeString(b_email)
            writeString(b_content)
            writeString(b_time)
            writeString(nickname)
            writeString(islike)
            writeInt(b_like)
            writeString(b_img)
            writeString(img)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Board> {
        override fun createFromParcel(parcel: Parcel): Board {
            return Board(parcel)
        }

        override fun newArray(size: Int): Array<Board?> {
            return arrayOfNulls(size)
        }
    }
}
