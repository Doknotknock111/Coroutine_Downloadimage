package com.example.cse226_ca1

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {
    @GET
    suspend fun downloadImage(@Url url: String): Response<ResponseBody>
}


// Retrofit interface to download the image