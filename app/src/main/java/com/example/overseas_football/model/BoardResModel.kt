package com.example.overseas_football.model


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
        val b_nickname: String,
        val b_like: Int,
        val b_img: String? = ""
)