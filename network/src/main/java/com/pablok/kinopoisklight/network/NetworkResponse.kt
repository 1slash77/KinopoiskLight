package com.pablok.kinopoisklight.network

sealed class NetworkResponse<out T> {
    data class Success<T>(val data:T?) : NetworkResponse<T>()
    data class Error(val errorMsg: String?) : NetworkResponse<Nothing>()
}