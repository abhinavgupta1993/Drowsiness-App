package com.example.viewModel.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SettingsViewModel : ViewModel() {
    private val _seekBarValue = MutableLiveData<Int>()
    val seekBarValue: LiveData<Int>
        get() = _seekBarValue
    init {
// Set initial value for the SeekBar
        _seekBarValue.value = 0
    }
    fun setSeekBarValue(value: Int) {
        _seekBarValue.value = value
    }
}
