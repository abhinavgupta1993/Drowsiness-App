package com.drowsiness.ai.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityHomeBinding
import com.drowsiness.ai.viewModel.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

    }
}