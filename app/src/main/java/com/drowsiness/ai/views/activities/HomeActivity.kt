package com.drowsiness.ai.views.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityHomeBinding
import com.drowsiness.ai.facedetection.camera.CameraManager
import com.drowsiness.ai.helper.DetectionHelper
import com.drowsiness.ai.viewModel.viewmodels.HomeViewModel


class HomeActivity : AppCompatActivity(), DetectionHelper {

    private lateinit var cameraManager: CameraManager
    lateinit var homeBinding: ActivityHomeBinding
    lateinit var homeViewModel: HomeViewModel
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            homeBinding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        homeBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle?.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        homeBinding.openMenu.setOnClickListener {
            when (count) {
                0 -> {
                    homeBinding.drawerLayout.openDrawer(GravityCompat.END)
                    count = 1
                }

                1 -> {
                    homeBinding.drawerLayout.closeDrawer(GravityCompat.END)
                    count = 0
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                homeBinding.drawerLayout.closeDrawer(GravityCompat.END)
            }
        })


        cameraManager = CameraManager(
            this,
            homeBinding.viewCameraPreview,
            homeBinding.viewGraphicOverlay,
            this
        )

        homeBinding.onOffCamera.setOnCheckedChangeListener { _, isChecked ->

            when (isChecked) {
                true -> {
                    homeBinding.fl.visibility = View.VISIBLE
                    cameraManager.cameraStart()
                }

                false -> {
                    homeBinding.fl.visibility = View.GONE
                    cameraManager.cameraStop()
                }

            }

        }
        askCameraPermission()
        buttonClicks()
    }
    private fun buttonClicks() {
        homeBinding.buttonTurnCamera.setOnClickListener {
            cameraManager.changeCamera()
        }
//        homeBinding.buttonStopCamera.setOnClickListener {
//            cameraManager.cameraStop()
//            buttonVisibility(false)
//        }
//        homeBinding.buttonStartCamera.setOnClickListener {
//            cameraManager.cameraStart()
//            buttonVisibility(true)
//        }
    }

//    private fun buttonVisibility(forStart: Boolean) {
//        if (forStart) {
//            homeBinding.buttonStopCamera.visibility = View.VISIBLE
//            homeBinding.buttonStartCamera.visibility = View.INVISIBLE
//        } else {
//            homeBinding.buttonStopCamera.visibility = View.INVISIBLE
//            homeBinding.buttonStartCamera.visibility = View.VISIBLE
//        }
//    }

    private fun askCameraPermission() {
        if (arrayOf(android.Manifest.permission.CAMERA).all {
                ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            }) {
            cameraManager.cameraStart()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            cameraManager.cameraStart()
        } else {
            Toast.makeText(this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun checkYawningCount(yawningCount: Int) {

    }

    override fun checkEyesCount(eyesCount: Int) {

    }

    override fun checkDistractionCount(distractionCount: Int) {

    }

}