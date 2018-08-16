package com.example.overseas_football.model


data class CommentResModel(
        val result: String,
        val commentList: List<Comment>
)

data class Comment(
        val c_index: Int = -1,
        val c_child_index: Int = -1,
        val c_email: String = "",
        val c_comment: String="",
        val c_time: String="",
        val img: String? = "",
        val nickname: String=""
)
