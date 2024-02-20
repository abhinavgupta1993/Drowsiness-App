package com.drowsiness.ai.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityProfileBinding
import com.drowsiness.ai.viewModel.modelFactories.ProfileViewModelFactory
import com.drowsiness.ai.viewModel.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    // View Binding
    lateinit var profileBinding: ActivityProfileBinding
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this is view Binding
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        profileViewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory()
        )[ProfileViewModel::class.java]

        profileBinding.profileViewModel = profileViewModel
        profileBinding.lifecycleOwner = this


    }
}