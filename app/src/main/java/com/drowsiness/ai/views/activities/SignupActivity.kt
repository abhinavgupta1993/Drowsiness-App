package com.drowsiness.ai.views.activities
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivitySignupBinding
import com.drowsiness.ai.helper.Constants
import com.drowsiness.ai.repository.DrowsinessRepository
import com.drowsiness.ai.retrofit.APIService
import com.drowsiness.ai.retrofit.RetrofitHelper
import com.drowsiness.ai.viewModel.modelFactories.SignupViewModelFactory
import com.drowsiness.ai.viewModel.viewmodels.SignUpViewModel


// name - Abhinav Gupta
// created at 13th Feb 2024


class SignupActivity : AppCompatActivity() {

    private lateinit var signupBinding: ActivitySignupBinding
    private var signUpViewModel: SignUpViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        signUpViewModel =
            ViewModelProvider(this,
                SignupViewModelFactory(DrowsinessRepository(RetrofitHelper.getInstance().create(
                    APIService::class.java)))
            )[SignUpViewModel::class.java]
        signupBinding.signUpViewModel = signUpViewModel
        signupBinding.lifecycleOwner = this

        signupBinding.welcome2.typeface = Constants.customTypefaceNunitoRegular(this)
        signupBinding.welcome3.typeface = Constants.customTypefaceNunitoRegular(this)
        signupBinding.signup.typeface = Constants.customTypefaceNunitoRegular(this)

        // here connected by viewModel and activity
        // why we did that? because when we fetch device ID it needs context of the class so viewModel do not have
        // the context of the class activity have here "this"
        signUpViewModel?.getDeviceID(Constants.getDeviceID(this))

        // here we are generating the Toast messages
        signUpViewModel?.toastMessage?.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


        signUpViewModel?.dialogCondition?.observe(this) { condition ->
            if (condition) {
                Constants.showDialog(this, true)
            } else {
                Constants.dismissDialog(true)
            }
        }

        signUpViewModel?.emailCheckConditions?.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    signupBinding.email.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    signupBinding.email.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    signupBinding.email.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }

        signUpViewModel?.nameCheckConditions?.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    signupBinding.name.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    signupBinding.name.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    signupBinding.name.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }

        signUpViewModel?.passwordCheckConditions?.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    signupBinding.password.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    signupBinding.password.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    signupBinding.password.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }

        signUpViewModel?.confirmPasswordCheckConditions?.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    signupBinding.confirmPassword.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    signupBinding.confirmPassword.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    signupBinding.confirmPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }

        signUpViewModel?.passwordMatchCheckConditions?.observe(this) {
            Log.e("checkConditions", "checkConditions $it")
            when (it) {
                true -> {
                    signupBinding.password.setBackgroundResource(R.drawable.bg_background_green)
                    signupBinding.confirmPassword.setBackgroundResource(R.drawable.bg_background_green)
                }
                else ->{
                    signupBinding.password.setBackgroundResource(R.drawable.bg_backgroung_red)
                    signupBinding.confirmPassword.setBackgroundResource(R.drawable.bg_backgroung_red)
//                    signupBinding.password.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
//                    signupBinding.confirmPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                }
            }
        }


    }
}