package com.drowsiness.ai.repository

import android.util.Log
import com.drowsiness.ai.model.login.LoginRequest
import com.drowsiness.ai.model.login.LoginResponse
import com.drowsiness.ai.model.signup.SignUpRequest
import com.drowsiness.ai.model.signup.SignUpResponse
import com.drowsiness.ai.retrofit.APIService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// name - Abhinav Gupta
// created at 13th Feb 2024

class DrowsinessRepository(private val apiService: APIService) {

    fun getSignup(
        signUpRequest: SignUpRequest,
        apiResponseListener: APIResponseListener<SignUpResponse?>
    ) {
        Log.e("getSignup", "SignUpRequest ${Gson().toJson(signUpRequest)}")

        val result = apiService.signup(signUpRequest)
        result.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.body() != null) {
                    apiResponseListener.onReceiveResponse(response.body()!!)
                    println("SignUpResponse - ${Gson().toJson(response.body())}")
                } else {
                    println("SignUpResponse ELSE - ${Gson().toJson(response.headers())}")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                println("SignUpResponseFAILURE : $call")
                t.printStackTrace()
                apiResponseListener.onFailure()
            }

        })
    }

    fun getLogin(
        loginRequest: LoginRequest,
        apiResponseListener: APIResponseListener<LoginResponse?>
    ) {
        Log.e("getLogin", "LoginRequest ${Gson().toJson(loginRequest)}")

        val result = apiService.login(loginRequest)
        result.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.body() != null) {
                    apiResponseListener.onReceiveResponse(response.body()!!)
                    println("LoginResponse - ${Gson().toJson(response.body())}")
                } else {
                    println("LoginResponse ELSE - ${Gson().toJson(response.headers())}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                println("LoginResponseFailure : $call")
                t.printStackTrace()
                apiResponseListener.onFailure()
            }

        })
    }



    interface APIResponseListener<T> {
        fun onReceiveResponse(response: T)
        fun onStatusFailed(response: T)
        fun onFailure()
    }
}