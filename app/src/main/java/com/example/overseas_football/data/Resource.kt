package com.example.overseas_football.data


class Resource<out T>(val status: Status, private val data: T?, val throwable: Throwable?) {
    val loading
        get() = status == Status.LOADING
    val error
        get() = status == Status.ERROR
    val success
        get() = status == Status.SUCCESS

    fun getData(): T? = data

    companion object {
        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
        fun <T> error(throwable: Throwable): Resource<T> = Resource(Status.ERROR, null, throwable)
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, null)
    }
}