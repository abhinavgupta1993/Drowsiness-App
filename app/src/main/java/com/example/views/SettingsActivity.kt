package com.example.views
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivitySettingsBinding
import com.example.viewModel.modelFactories.SettingsViewModelFactory
import com.example.viewModel.viewmodels.SettingsViewModel

class
SettingsActivity : AppCompatActivity() {

    lateinit var settingsBinding: ActivitySettingsBinding
    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsBinding= DataBindingUtil.setContentView(this, R.layout.activity_settings)
        settingsViewModel=ViewModelProvider(this,SettingsViewModelFactory())[settingsViewModel::class.java]
        settingsBinding.settingsViewModel= settingsViewModel
        settingsBinding.lifecycleOwner= this
        settingsBinding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    settingsViewModel.setSeekBarValue(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
             // Do something on touch start if needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
             // Do something on touch end if needed
            }
        })
    }
}