package com.drowsiness.ai.views.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityHomeBinding
import com.drowsiness.ai.facedetection.camera.CameraManager
import com.drowsiness.ai.viewModel.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var cameraManager: CameraManager
    lateinit var homeBinding: ActivityHomeBinding
    lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        cameraManager = CameraManager(
            this,
            homeBinding.viewCameraPreview,
            homeBinding.viewGraphicOverlay,
            this
        )
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
}