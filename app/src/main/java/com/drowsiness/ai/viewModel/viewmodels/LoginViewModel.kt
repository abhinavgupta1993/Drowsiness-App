package com.drowsiness.ai.viewModel.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drowsiness.ai.helper.Constants

class LoginViewModel : ViewModel() {

    val inputLoginEmail : MutableLiveData<String> = MutableLiveData()
    val inputLoginPassword : MutableLiveData<String> = MutableLiveData()

    // take values in boolean
    private val isValidEmail = MutableLiveData<Boolean>()
    private val isValidPassword = MutableLiveData<Boolean>()

    // toast message acc to condition type
    val toastMessage = MutableLiveData<String>()
    val readyToLogin = MutableLiveData<Boolean>()

    init {
        isValidEmail.value = false
        isValidPassword.value = false
        readyToLogin.value = false
    }

    // login result q lena h abhi jb tk aap logo ne user se input nhi liya????

    fun onClick(){
        Log.e("LoginViewModel", "loginCreds :- ${inputLoginEmail.value}, ${inputLoginPassword.value}")

        if (isValidEmail.value == Constants.isEmailValid(inputLoginEmail.value.toString())) {
            toastMessage.value = "Email should be in format"
            // checking is name having at least 3 chars
        } else if (isValidPassword.value == Constants.isPasswordEmpty(inputLoginPassword.value.toString())) {
            toastMessage.value = "Password should have at least 8 characters"
            // checking is password empty or less than 8 chars
        } else {

            toastMessage.value = "All conditions matched"
            readyToLogin.value = true

                // hitting API and getting response for SIGNUP API..
//                drowsinessSignup(inputConfirmPassword.value.toString())
        }

    }

}