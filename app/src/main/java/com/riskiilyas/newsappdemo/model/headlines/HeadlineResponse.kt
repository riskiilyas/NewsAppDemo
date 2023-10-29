package com.riskiilyas.newsappdemo.model.headlines

data class HeadlineResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)