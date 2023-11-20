package com.d204.algo.data.api

// api try catch 문
internal inline fun <T> handleApi(transform: () -> T): NetworkResult<T> = try {
    NetworkResult.Success(transform.invoke())
} catch (e: Exception) {
    NetworkResult.Error(e)
}
