package com.riskiilyas.newsappdemo.model.everything

data class EverythingResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)