package com.drowsiness.ai.viewModel

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

class SignUpViewModel(var drowsinessRepository: DrowsinessRepository) : ViewModel() {

    //Inputs variables by which come from user
    var inputEmail: MutableLiveData<String> = MutableLiveData()
    var inputName: MutableLiveData<String> = MutableLiveData()
    var inputPassword: MutableLiveData<String> = MutableLiveData()
    var inputConfirmPassword: MutableLiveData<String> = MutableLiveData()

    // toast message acc to condition type
    val toastMessage = MutableLiveData<String>()

    // take values in boolean
    private val isValidEmail = MutableLiveData<Boolean>()
    private val isValidName = MutableLiveData<Boolean>()
    private val isValidPassword = MutableLiveData<Boolean>()
    private val isValidConfirmPassword = MutableLiveData<Boolean>()
    val dialogCondition = MutableLiveData<Boolean>()

    var deviceIdd: String? = null

    // when SignUpViewModel calls these values will have default values
    init {
        isValidEmail.value = false
        isValidName.value = false
        isValidPassword.value = false
        isValidConfirmPassword.value = false
        dialogCondition.value = false
    }

    // getting device ID by connecting the activity
    fun getDeviceID(deviceId: String) {
        deviceIdd = deviceId
    }

    fun onClick() {

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
        } else if (isValidConfirmPassword.value == Constants.isPasswordEmpty(inputConfirmPassword.value.toString())) {
            toastMessage.value = "Confirm Password should have at least 8 characters"
        } else {
            // checking is password and confirm password matches?
            if (!isPasswordMatch(inputPassword.value.toString(), inputConfirmPassword.value.toString())) {
                toastMessage.value = "Password does not matched"
            } else {
                // hitting API and getting response for SIGNUP API..
                drowsinessSignup(inputConfirmPassword.value.toString())
            }
        }

    }

    private fun drowsinessSignup(confirmPassword: String) {
        dialogCondition.value = true
        val signUpRequest = SignUpRequest(
            email = inputEmail.value.toString().trim(),
            name = inputName.value.toString(),
            deviceId = deviceIdd!!,
            password = confirmPassword
        )

//        CoroutineScope(Dispatchers.IO).launch {
            drowsinessRepository.getSignup(signUpRequest, object :
                DrowsinessRepository.APIResponseListener<SignUpResponse?> {

                override fun onFailure() {
                    dialogCondition.value = false
                    toastMessage.value = "Internet connection!"
                }

                override fun onReceiveResponse(response: SignUpResponse?) {
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
//        }
    }
}