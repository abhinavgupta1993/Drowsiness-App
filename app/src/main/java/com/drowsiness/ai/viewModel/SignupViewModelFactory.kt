package com.drowsiness.ai.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.repository.DrowsinessRepository
import java.lang.IllegalArgumentException

// name - Abhinav Gupta
// created at 13th Feb 2024

class SignupViewModelFactory(private val drowsinessRepository: DrowsinessRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(drowsinessRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}