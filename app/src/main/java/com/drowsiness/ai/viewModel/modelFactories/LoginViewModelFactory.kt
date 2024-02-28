package com.drowsiness.ai.viewModel.modelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.repository.DrowsinessRepository
import com.drowsiness.ai.viewModel.viewmodels.LoginViewModel

class LoginViewModelFactory(private val drowsinessRepository: DrowsinessRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(drowsinessRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}