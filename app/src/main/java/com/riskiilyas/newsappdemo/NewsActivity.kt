package com.riskiilyas.newsappdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.riskiilyas.newsappdemo.databinding.ActivityNewsBinding


class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        val intent = intent
        val message = intent.getStringExtra("url")
        binding.webview.loadUrl(message.toString())

        setContentView(binding.root)
    }
}