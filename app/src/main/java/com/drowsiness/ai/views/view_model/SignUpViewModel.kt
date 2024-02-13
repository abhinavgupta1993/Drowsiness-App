package com.drowsiness.ai.views.view_model

import android.util.Log
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel(), Observable {

    @Bindable
    var inputEmail = MutableLiveData<String>()

    @Bindable
    var inputName = MutableLiveData<String>()

    @Bindable
    var inputPassword = MutableLiveData<String>()

    @Bindable
    var inputConfirmPassword = MutableLiveData<String>()

    var _errorToast = MutableLiveData<Boolean>()




    fun onClick() {
        if (inputEmail.value.isNullOrEmpty()){
            _errorToast.value = true
        } else if (inputName.value.isNullOrEmpty()){
            _errorToast.value = true
        }else if (inputPassword.value.isNullOrEmpty()){
            _errorToast.value = true
        }else if (inputConfirmPassword.value.isNullOrEmpty()){
            _errorToast.value = true
        }else{
            //API logic will be here
            _errorToast.value = false

        }
    }



    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}

private fun <T> MutableLiveData<T>.isNullOrBlank(): Boolean {
    TODO("Not yet implemented")
}
