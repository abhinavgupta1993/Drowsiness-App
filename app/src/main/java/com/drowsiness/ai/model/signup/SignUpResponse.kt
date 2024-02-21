package com.drowsiness.ai.model.signup

data class SignUpResponse(
    val msg: String, // User already registered
    val success: Int // 202
)