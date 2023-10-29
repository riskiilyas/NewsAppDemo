package com.riskiilyas.newsappdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riskiilyas.newsappdemo.databinding.ActivityMainBinding
import com.riskiilyas.newsappdemo.model.everything.EverythingResponse
import com.riskiilyas.newsappdemo.model.headlines.HeadlineResponse
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    lateinit var binding: ActivityMainBinding
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        newsAdapter = NewsAdapter{
            val intent = Intent(this@MainActivity, NewsActivity::class.java)
            intent.putExtra("url", it.url)
            startActivity(intent)
        }

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        setContentView(binding.root)

        listenState()
        viewModel.fetchHeadline()
        viewModel.fetchEverything()
    }

    private fun listenState() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.everythingState.collect {
                    when (it) {
                        is Resource.Success<EverythingResponse> -> newsAdapter.changeList(it.data.articles)
                        is Resource.Error<EverythingResponse> -> return@collect
                        is Resource.Loading<EverythingResponse> -> return@collect
                    }
                }
            }
        }

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.headlineState.collect {
                    when (it) {
                        is Resource.Success<HeadlineResponse> -> {
                            val headline = it.data.articles[0]
                            binding.textView5.text = headline.source.name
                            binding.textView4.text = headline.title
                        }
                        is Resource.Error<HeadlineResponse> -> return@collect
                        is Resource.Loading<HeadlineResponse> -> return@collect
                    }
                }
            }
        }
    }
}