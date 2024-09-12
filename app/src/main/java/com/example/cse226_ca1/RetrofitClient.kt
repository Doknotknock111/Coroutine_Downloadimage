package com.example.cse226_ca1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dummy-url.com/" // The base URL is required by Retrofit

    // Build the Retrofit object
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Gson can be replaced based on need
        .build()

    // Provide the ApiService instance
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}




// Retrofit client to provide the Retrofit instance