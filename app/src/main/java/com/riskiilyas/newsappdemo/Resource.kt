package com.riskiilyas.newsappdemo

import java.lang.Exception

sealed class Resource<T>{
    class Error<T>(val msg: String): Resource<T>()
    class Loading<T>: Resource<T>()
    class  Success<T>(val data: T): Resource<T>()
}
