package com.drowsiness.ai.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drowsiness.ai.databinding.ActivitySignupBinding
import com.drowsiness.ai.helper.Constants

class SignupActivity : AppCompatActivity() {

    private lateinit var signupBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)

        signupBinding.welcome2.typeface = Constants.customTypefaceNunitoRegular(this)
        signupBinding.welcome3.typeface = Constants.customTypefaceNunitoRegular(this)
        signupBinding.signup.typeface = Constants.customTypefaceNunitoRegular(this)

        signupBinding.signup.setOnClickListener {

        }

    }
}