package com.drowsiness.ai.views.activities
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivitySignupBinding
import com.drowsiness.ai.helper.Constants
import com.drowsiness.ai.views.view_model.SignUpViewModel
import com.drowsiness.ai.views.view_model.SignupViewModelFactory



class SignupActivity : AppCompatActivity(){
//    ,View.OnClickListener, View.OnFocusChangeListener,View.OnKeyListener{

    private lateinit var signupBinding: ActivitySignupBinding
    private var signUpViewModel : SignUpViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        signUpViewModel = ViewModelProvider(this, SignupViewModelFactory())[SignUpViewModel::class.java]
        signupBinding.signUpViewModel = signUpViewModel
        signupBinding.lifecycleOwner = this

        signupBinding.welcome2.typeface = Constants.customTypefaceNunitoRegular(this)
        signupBinding.welcome3.typeface = Constants.customTypefaceNunitoRegular(this)
        signupBinding.signup.typeface = Constants.customTypefaceNunitoRegular(this)

        signUpViewModel!!._errorToast.observe(this, Observer {hasError ->
            if (hasError == true){
                Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fields are filled", Toast.LENGTH_SHORT).show()
            }
        })



//        signupBinding.signup.setOnClickListener {
//
//            if (signupBinding.email.text.isNullOrEmpty()){
//                Toast.makeText(this,"Please enter a valid Email",Toast.LENGTH_SHORT).show()
//            } else if (signupBinding.name.text.isNullOrEmpty()){
//                Toast.makeText(this,"Please enter a valid Name",Toast.LENGTH_SHORT).show()
//            }else if (signupBinding.password.text.isNullOrEmpty()){
//                Toast.makeText(this,"Please enter a valid Password",Toast.LENGTH_SHORT).show()
//            } else if (signupBinding.password.text.toString() != signupBinding.confirmPassword.text.toString()){
//                Toast.makeText(this,"Passwords does not match",Toast.LENGTH_SHORT).show()
//            } else{
//                // upper conditions are false then this else will work and API will call here....
//                // but this should be done as in viewModel not here.
//            }
//        }
    }

//    private fun validateEmail():Boolean{
//        var errorMessage:String?=null
//        val value:String=signupBinding.email.text.toString()
//        if(value.isEmpty()){
//            errorMessage="Email is required"
//        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
//            errorMessage="Email address is invalid"
//        }
//
//        if(errorMessage!=null){
//            signupBinding.email.apply{
//                isErrorEnabled=true
//                error =errorMessage
//            }
//        }
//        return errorMessage==null
//    }
//    private fun validateFullName():Boolean{
//        var errorMessage:String?=null
//        val value:String=signupBinding.name.text.toString()
//        if(value.isEmpty()){
//            errorMessage="Full name is required"
//        }
//        if(errorMessage!=null){
//            signupBinding.name.apply{
//                isErrorEnabled=true
//                error=errorMessage
//            }
//        }
//        return errorMessage==null
//
//    }
//    private fun validatePassword():Boolean{
//        var errorMessage:String?=null
//        val value:String =signupBinding.password.text.toString()
//        if(value.isEmpty()){
//            errorMessage="Password is required"
//        }else if(value.length<6){
//            errorMessage="Password must be 6 characters long"
//        }
//
//        if(errorMessage!=null){
//            signupBinding.password.apply{
//                isErrorEnabled=true
//                error =errorMessage
//            }
//        }
//        return errorMessage==null
//    }
//    private fun validateConfirmPassword():Boolean{
//        var errorMessage:String?=null
//        val value:String =signupBinding.confirmPassword.text.toString()
//        if(value.isEmpty()){
//            errorMessage="Confirm Password is required"
//        }else if(value.length<6){
//            errorMessage="Confirm Password must be 6 characters long"
//        }
//        if(errorMessage!=null){
//            signupBinding.confirmPassword.apply{
//                isErrorEnabled=true
//                error =errorMessage
//            }
//        }
//        return errorMessage==null
//    }
//    private fun validatePasswordAndConfirmPassword():Boolean{
//        var errorMessage:String?=null
//        val password =signupBinding.password.text.toString()
//        val confirmPassword =signupBinding.confirmPassword.text.toString()
//        if(password!=confirmPassword){
//            errorMessage="Confirm password doesn'tmatch with password"
//        }
//
//        if(errorMessage!=null){
//            signupBinding.confirmPassword.apply{
//                isErrorEnabled=true
//                error =errorMessage
//            }
//        }
//        return errorMessage==null
//    }




//    override fun onClick(v: View?) {
//
//    }

//    override fun onFocusChange(v: View?, hasFocus: Boolean) {
//        if(v!=null){
//            when(v.id){
//                R.id.email->{
//                    if(hasFocus){
//                        if(signupBinding.email.){
//                            signupBinding.email.isErrorEnabled=false
//                        }
//                    }else{
//                        validateEmail()
//                    }
//
//                }
//                R.id.name->{
//                    if(hasFocus){
//                        if(signupBinding.name.isErrorEnabled){
//                            signupBinding.name.isErrorEnabled=false
//                        }
//
//                    }else{
//                        if(validateFullName()){
//                            //do validation for its uniqueness
//                        }
//                    }
//
//                }
//                R.id.password->{
//                    if(hasFocus){
//                        if(signupBinding.password.isErrorEnabled){
//                            signupBinding.password.isErrorEnabled=false
//                        }
//
//                    }else{
//                        if(validatePassword()&&signupBinding.confirmPassword.text!!.isNotEmpty()&&validateConfirmPassword()&&
//                            validatePasswordAndConfirmPassword()){
//                            if(signupBinding.confirmPassword.isErrorEnabled){
//                                signupBinding.confirmPassword.isErrorEnabled=false
//                            }
//                            signupBinding.confimpassword.apply {
//                                setStartIconDrawable(R.drawable.ic_android_black_24dp)
//                                setstartIconTintList(ColorStateList.valueOf(Color.BLACK))
//                            }
//                        }
//                    }
//
//                }
//                R.id.confirmPassword->{
//                    if(hasFocus){
//                        if(signupBinding.confirmPassword.isErrorEnabled){
//                            signupBinding.confirmPassword.isErrorEnabled=false
//                        }
//
//                    }else{
//                        if(validateConfirmPassword()&&validatePassword()&&validatePasswordAndConfirmPassword()){
//                            if(signupBinding.password.isErrorEnabled){
//                                signupBinding.password.isErrorEnabled=false
//                            }
//                            signupBinding.confirmPassword{
//                                setStartIconDrawable(R.drawable.ic_android_black_24dp)
//                                setstartIconTintList(ColorStateList.valueOf(Color.BLACK))
//                            }
//
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//
//
//    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
//        return false
//    }

}