package com.drowsiness.ai.viewModel.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drowsiness.ai.helper.Constants
import com.drowsiness.ai.helper.Constants.Companion.isNameLengthGreaterThan3
import com.drowsiness.ai.helper.Constants.Companion.isPasswordMatch
import com.drowsiness.ai.model.signup.SignUpRequest
import com.drowsiness.ai.model.signup.SignUpResponse
import com.drowsiness.ai.repository.DrowsinessRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// name - Abhinav Gupta
// created at 13th Feb 2024

class SignUpViewModel(private var drowsinessRepository: DrowsinessRepository) : ViewModel() {

    //Inputs variables by which come from user
    var inputEmail: MutableLiveData<String> = MutableLiveData()
    var inputName: MutableLiveData<String> = MutableLiveData()
    var inputPassword: MutableLiveData<String> = MutableLiveData()
    var inputConfirmPassword: MutableLiveData<String> = MutableLiveData()
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    // toast message acc to condition type
    val toastMessage = MutableLiveData<String>()
    val status = MutableLiveData<Int>()

    // take values in boolean
    private val isValidEmail = MutableLiveData<Boolean>()
    private val isValidName = MutableLiveData<Boolean>()
    private val isValidPassword = MutableLiveData<Boolean>()
    private val isValidConfirmPassword = MutableLiveData<Boolean>()
    val dialogCondition = MutableLiveData<Boolean>()

    var deviceIdd: String? = null
    var emailCheckConditions = MutableLiveData<Boolean>()
    var nameCheckConditions = MutableLiveData<Boolean>()
    var passwordCheckConditions = MutableLiveData<Boolean>()
    var confirmPasswordCheckConditions = MutableLiveData<Boolean>()
    var passwordMatchCheckConditions = MutableLiveData<Boolean>()
    var tvLoginHere = MutableLiveData<Boolean>()

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            emailCheckConditions.value = inputEmail.value.toString().matches(emailPattern.toRegex())
        }
    }

    val nameWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            nameCheckConditions.value = isNameLengthGreaterThan3(inputName.value.toString())
        }
    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            passwordCheckConditions.value = Constants.isPasswordEmpty(inputPassword.value.toString())
        }
    }

    val confirmPasswordWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            confirmPasswordCheckConditions.value = Constants.isPasswordEmpty(inputConfirmPassword.value.toString())
        }
    }



    // when SignUpViewModel calls these values will have default values
    init {
        isValidEmail.value = false
        isValidName.value = false
        isValidPassword.value = false
        isValidConfirmPassword.value = false
        dialogCondition.value = false
        tvLoginHere.value = false
        inputName.value = ""
        status.value = 0
    }

    // getting device ID by connecting the activity
    fun getDeviceID(deviceId: String) {
        deviceIdd = deviceId
    }

    fun onClick() {

        if (isValidEmail.value == Constants.isEmailValid(inputEmail.value.toString())
            && isValidName.value == isNameLengthGreaterThan3(inputName.value.toString())
            && isValidPassword.value == Constants.isPasswordEmpty(inputPassword.value.toString())
            &&isValidConfirmPassword.value == Constants.isPasswordEmpty(inputConfirmPassword.value.toString())){
            toastMessage.value = "Fields can not be empty"
            emailCheckConditions.value = false
            nameCheckConditions.value = false
            passwordCheckConditions.value = false
            confirmPasswordCheckConditions.value = false
        } else {
            // here we are checking the conditions and having values in boolean
            if (isValidEmail.value == Constants.isEmailValid(inputEmail.value.toString())) {
                toastMessage.value = "Email should be in format"
                inputName.value = ""
                // checking is name having at least 3 chars
            } else if (isValidName.value == isNameLengthGreaterThan3(inputName.value.toString())) {
                toastMessage.value = "Name should have at least 3 characters"
                // checking is password empty or less than or equals to 8
            } else if (isValidPassword.value == Constants.isPasswordEmpty(inputPassword.value.toString())) {
                toastMessage.value = "Password should have at least 8 characters"
                // checking is password empty or less than 8 chars
            } else if (isValidConfirmPassword.value == Constants.isPasswordEmpty(
                    inputConfirmPassword.value.toString()
                )
            ) {
                toastMessage.value = "Confirm Password should have at least 8 characters"
            } else {
                // checking is password and confirm password matches?
                if (!isPasswordMatch(
                        inputPassword.value.toString(),
                        inputConfirmPassword.value.toString()
                    )
                ) {
                    toastMessage.value = "Password does not matched"
                    passwordMatchCheckConditions.value = false
                } else {
                    // hitting API and getting response for SIGNUP API..
                    passwordMatchCheckConditions.value = true
                    emailCheckConditions.value = true
                    nameCheckConditions.value = true
                    passwordCheckConditions.value = true
                    confirmPasswordCheckConditions.value = true
                    drowsinessSignup(inputConfirmPassword.value.toString())
                }
            }
        }
    }

    fun tvLoginClick(){
        tvLoginHere.value = true
    }

    private fun drowsinessSignup(confirmPassword: String) {
        dialogCondition.value = true
        val signUpRequest = SignUpRequest(
            email = inputEmail.value.toString().trim(),
            name = inputName.value.toString(),
            deviceId = deviceIdd!!,
            password = confirmPassword
        )

        CoroutineScope(Dispatchers.IO).launch {
            drowsinessRepository.getSignup(signUpRequest, object :
                DrowsinessRepository.APIResponseListener<SignUpResponse?> {

                override fun onFailure() {
                    dialogCondition.value = false
                    toastMessage.value = "Server Error"
                }
                override fun onReceiveResponse(response: SignUpResponse?) {
                    status.value = response?.success
                    when (response?.success) {
                        200 -> {
                            dialogCondition.value = false
                            toastMessage.value = response.msg
                        }

                        201 -> {
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

                override fun onStatusFailed(response: SignUpResponse?) {
                    println("onStatusFailed - FetchViaMobileRequest : " + response?.msg)
                    dialogCondition.value = false
                }
            })
        }
    }
}