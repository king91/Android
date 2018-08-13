package com.example.overseas_football.model



data class CommentResModel(
    val result: String,
    val commentList: List<Comment>
)

data class Comment(
    val c_index: Int,
    val c_child_index: Int,
    val c_email: String,
    val c_comment: String,
    val c_time: String
)
