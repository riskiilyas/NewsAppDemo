package com.riskiilyas.newsappdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.riskiilyas.newsappdemo.model.everything.EverythingResponse
import com.riskiilyas.newsappdemo.model.headlines.HeadlineResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val apiService: ApiService
): ViewModel() {
    val headlineState = MutableStateFlow<Resource<HeadlineResponse>>(Resource.Loading())
    val everythingState = MutableStateFlow<Resource<EverythingResponse>>(Resource.Loading())

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val apiService = ApiConfig.getApiService()
                MainViewModel(apiService)
            }
        }
    }

    fun fetchHeadline() {
        viewModelScope.launch {
            everythingState.emit(Resource.Loading())
            apiService.getHeadlines().enqueue(object : Callback<HeadlineResponse> {
                override fun onResponse(call: Call<HeadlineResponse>, response: Response<HeadlineResponse>) {
                    GlobalScope.launch {
                        response.body()?.let {
                            println(it)
                            headlineState.emit(Resource.Success(it))
                        }
                    }
                }

                override fun onFailure(call: Call<HeadlineResponse>, t: Throwable) {
                    GlobalScope.launch {
                        println(t)
                        everythingState.emit(Resource.Error(t.message.toString()))
                    }
                }
            })
        }
    }

    fun fetchEverything() {
        viewModelScope.launch {
            everythingState.emit(Resource.Loading())
            apiService.getEverything().enqueue(object : Callback<EverythingResponse> {
                override fun onResponse(call: Call<EverythingResponse>, response: Response<EverythingResponse>) {
                    GlobalScope.launch {
                        response.body()?.let {
                            everythingState.emit(Resource.Success(it))
                        }
                    }
                }

                override fun onFailure(call: Call<EverythingResponse>, t: Throwable) {
                    GlobalScope.launch {
                        everythingState.emit(Resource.Error(t.message.toString()))
                    }
                }
            })
        }
    }

}