package com.drowsiness.ai.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityLoginBinding
import com.drowsiness.ai.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}