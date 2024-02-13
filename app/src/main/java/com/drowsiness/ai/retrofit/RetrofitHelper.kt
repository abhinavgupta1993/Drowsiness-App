package com.drowsiness.ai.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// name - Abhinav Gupta
// created at 13th Feb 2024

object RetrofitHelper {

    private const val BASE_URL = "http://3.110.14.254:8000/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}