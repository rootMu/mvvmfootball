package com.matthew.mvvmfootball.network

interface ApiRequestInterface<out T> {

    fun performRequest(): Result<T>

    fun cancel()
}