package com.riskiilyas.newsappdemo

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private var SERVICE: ApiService? = null

    fun getApiService(): ApiService {
        if (SERVICE == null) {
            val client = OkHttpClient.Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            SERVICE = retrofit.create(ApiService::class.java)
        }

        return SERVICE!!
    }
}