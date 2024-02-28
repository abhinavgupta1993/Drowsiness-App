package com.drowsiness.ai.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityLoginBinding
import com.drowsiness.ai.repository.DrowsinessRepository
import com.drowsiness.ai.retrofit.APIService
import com.drowsiness.ai.retrofit.RetrofitHelper
import com.drowsiness.ai.viewModel.viewmodels.LoginViewModel
import com.drowsiness.ai.viewModel.modelFactories.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(
                DrowsinessRepository(
                    RetrofitHelper.getInstance().create(
                APIService::class.java))
            )
        )[LoginViewModel::class.java]

        loginBinding.loginViewModel = loginViewModel
        loginBinding.lifecycleOwner = this

        loginViewModel.toastMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.readyToLogin.observe(this, Observer { message ->
            if (message == true) {
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
        })

        loginViewModel.clickTheSign.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(this, SignupActivity::class.java))
                finish()
            }
        })

        loginViewModel.emailCheckConditions.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    loginBinding.email.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    loginBinding.email.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    loginBinding.email.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }

        loginViewModel.passwordCheckConditions.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    loginBinding.password.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    loginBinding.password.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    loginBinding.password.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }
    }
}