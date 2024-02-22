package com.drowsiness.ai.retrofit

import com.drowsiness.ai.model.signup.SignUpRequest
import com.drowsiness.ai.model.signup.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// name - Abhinav Gupta
// created at 13th Feb 2024

interface APIService {

    @POST("signup/")
    fun signup(@Body signUpRequest: SignUpRequest?): Call<SignUpResponse>

}