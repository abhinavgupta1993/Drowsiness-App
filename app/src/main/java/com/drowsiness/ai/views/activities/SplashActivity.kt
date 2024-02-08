package com.drowsiness.ai.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.drowsiness.ai.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var splashBinding: ActivitySplashBinding

    private fun setSystemUIVisibility(hide: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val window = window.insetsController!!
            val windows = WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            if (hide) window.hide(windows) else window.show(windows)
            // needed for hide, doesn't do anything in show
            window.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            val view = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.decorView.systemUiVisibility = if (hide) view else view.inv()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        setSystemUIVisibility(true)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this@SplashActivity, GetStartedActivity::class.java))
                finish()
            }, 2250
        )
    }
}