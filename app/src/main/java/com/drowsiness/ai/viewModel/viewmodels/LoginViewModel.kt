package com.drowsiness.ai.viewModel.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drowsiness.ai.helper.Constants
import com.drowsiness.ai.model.login.LoginRequest
import com.drowsiness.ai.model.login.LoginResponse
import com.drowsiness.ai.model.signup.SignUpRequest
import com.drowsiness.ai.model.signup.SignUpResponse
import com.drowsiness.ai.repository.DrowsinessRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private var drowsinessRepository: DrowsinessRepository) : ViewModel() {

    val inputLoginEmail: MutableLiveData<String> = MutableLiveData()
    val inputLoginPassword: MutableLiveData<String> = MutableLiveData()

    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    // take values in boolean
    private val isValidEmail = MutableLiveData<Boolean>()
    private val isValidPassword = MutableLiveData<Boolean>()

    // toast message acc to condition type
    val toastMessage = MutableLiveData<String>()
    val readyToLogin = MutableLiveData<Boolean>()

    var emailCheckConditions = MutableLiveData<Boolean>()
    var clickTheSign = MutableLiveData<Boolean>()
    var passwordCheckConditions = MutableLiveData<Boolean>()
    val dialogCondition = MutableLiveData<Boolean>()
    val status = MutableLiveData<Int>()


    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            emailCheckConditions.value =
                inputLoginEmail.value.toString().matches(emailPattern.toRegex())
        }
    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            passwordCheckConditions.value =
                Constants.isPasswordEmpty(inputLoginPassword.value.toString())
        }
    }


    init {
        isValidEmail.value = false
        isValidPassword.value = false
        readyToLogin.value = false
        dialogCondition.value = false
        clickTheSign.value = false
        status.value = 0
    }

    // login result q lena h abhi jb tk aap logo ne user se input nhi liya????

    fun onClick() {
        Log.e(
            "LoginViewModel",
            "loginCreds :- ${inputLoginEmail.value}, ${inputLoginPassword.value}"
        )

        if (isValidEmail.value == Constants.isEmailValid(inputLoginEmail.value.toString())
            && isValidPassword.value == Constants.isPasswordEmpty(inputLoginPassword.value.toString())
        ) {
            toastMessage.value = "Fields can not be empty"
            emailCheckConditions.value = false
            passwordCheckConditions.value = false
        } else {

            if (isValidEmail.value == Constants.isEmailValid(inputLoginEmail.value.toString())) {
                toastMessage.value = "Email should be in format"

                // checking is name having at least 3 chars
            } else if (isValidPassword.value == Constants.isPasswordEmpty(inputLoginPassword.value.toString())) {
                toastMessage.value = "Password should have at least 8 characters"

                // checking is password empty or less than 8 chars
            } else {

                emailCheckConditions.value = true
                passwordCheckConditions.value = true
                login()

            }
        }
    }

    fun tvSignClick(){
        clickTheSign.value = true
    }

    fun login(){
        dialogCondition.value = true
        val loginRequest = LoginRequest(
            email = inputLoginEmail.value.toString().trim(),
            password = inputLoginPassword.value.toString().trim()
        )

        CoroutineScope(Dispatchers.IO).launch {
            drowsinessRepository.getLogin(loginRequest, object :
                DrowsinessRepository.APIResponseListener<LoginResponse?> {

                override fun onFailure() {
                    dialogCondition.value = false
                    toastMessage.value = "Server Error"
                }
                override fun onReceiveResponse(response: LoginResponse?) {
                    status.value = response?.success
                    when (response?.success) {
                        200 -> {
                            readyToLogin.value = true
                            dialogCondition.value = false
                            toastMessage.value = response.msg
                        }

                        201 -> {
                            println("HEYMSG ${response.msg}")
                            dialogCondition.value = false
                            toastMessage.value = response.msg
                        }

                        202 -> {
                            dialogCondition.value = false
                            toastMessage.value = response.msg
                        }
                        else -> {
                            dialogCondition.value = false
                            Log.e("ViewMODEL", "else ${response?.success}")
                        }
                    }
                }

                override fun onStatusFailed(response: LoginResponse?) {
                    println("onStatusFailed - FetchViaMobileRequest : " + response?.msg)
                    dialogCondition.value = false
                }
            })
        }
    }

}