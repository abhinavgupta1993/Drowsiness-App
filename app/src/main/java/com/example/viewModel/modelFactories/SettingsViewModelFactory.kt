package com.example.viewModel.modelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewModel.viewmodels.SettingsViewModel
import java.lang.IllegalArgumentException

class SettingsViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel() as T
        }
        throw IllegalArgumentException("UNKNOWN ViewModel class")
    }
}

// or koi error?nI ABHI CPY KR RHI BS PUSH KRKE BTATI HOO