package com.riskiilyas.newsappdemo

import com.riskiilyas.newsappdemo.model.everything.EverythingResponse
import com.riskiilyas.newsappdemo.model.headlines.HeadlineResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val API_KEY = "YOUR_API_KEY"
        const val NEWS_QUERY = "Indonesia"
        const val COUNTRY = "ID"
    }

    @GET("v2/top-headlines?country=$COUNTRY&apiKey=$API_KEY")
    fun getHeadlines() : Call<HeadlineResponse>

    @GET("v2/everything?q=$NEWS_QUERY&apiKey=$API_KEY")
    fun getEverything() : Call<EverythingResponse>
}