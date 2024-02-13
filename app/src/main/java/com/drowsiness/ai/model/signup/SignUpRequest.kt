package com.drowsiness.ai.model.signup


// name - Abhinav Gupta
// created at 13th Feb 2024

data class SignUpRequest(
    val email: String,
    val name: String,
    val deviceId: String,
    val password: String
)