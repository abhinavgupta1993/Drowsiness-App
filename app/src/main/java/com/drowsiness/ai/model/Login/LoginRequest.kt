package com.drowsiness.ai.model.Login

class LoginRequest {
    data class LoginRequest(
        val email: String,
        val password: String
    )
}