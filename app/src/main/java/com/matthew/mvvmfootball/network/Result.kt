package com.matthew.mvvmfootball.network

import java.lang.Exception

sealed class Result<out T> {

    data class Success<out T: Any>(val result: T): Result<T>()

    data class Error(val exception: Exception): Result<Nothing>()
}