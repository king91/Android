package com.example.overseas_football.model

import android.os.Parcel
import android.os.Parcelable


data class CommentResModel(
        val result: String,
        val commentList: List<Comment>
)

data class Comment(
        val c_index: Int = -1,
        val c_child_index: Int = -1,
        val c_email: String = "",
        var c_comment: String = "",
        val c_time: String = "",
        val img: String? = "",
        val nickname: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(c_index)
        parcel.writeInt(c_child_index)
        parcel.writeString(c_email)
        parcel.writeString(c_comment)
        parcel.writeString(c_time)
        parcel.writeString(img)
        parcel.writeString(nickname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}
