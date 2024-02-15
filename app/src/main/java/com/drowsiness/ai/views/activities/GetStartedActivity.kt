package com.drowsiness.ai.views.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityGetStartedBinding
import com.drowsiness.ai.helper.Constants

class GetStartedActivity : AppCompatActivity() {

    private lateinit var getStartedBinding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getStartedBinding = DataBindingUtil.setContentView(this, R.layout.activity_get_started)

        getStartedBinding.btCreateAccount.typeface = Constants.customTypefaceNunitoRegular(this)
        getStartedBinding.btCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        getStartedBinding.btLogIn.typeface = Constants.customTypefaceNunitoRegular(this)
        getStartedBinding.btLogIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        getStartedBinding.tvTerms.typeface = Constants.customTypefaceNunitoRegular(this)
        getStartedBinding.tvAppName.typeface = Constants.customTypefaceNunitoRegular(this)

        val images = listOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        val adapter = ViewPagerAdapter(images)
        getStartedBinding.viewPager.adapter = adapter

        getStartedBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor()
            }

        })
    }
    fun changeColor(){
        when (getStartedBinding.viewPager.currentItem) {
            0 -> {
                getStartedBinding.image1.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.active
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                getStartedBinding.image2.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red_200
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                getStartedBinding.image3.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red_200
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }

            1 -> {
                getStartedBinding.image1.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red_200
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                getStartedBinding.image2.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.active
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                getStartedBinding.image3.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red_200
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
            2-> {
                getStartedBinding.image1.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red_200
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                getStartedBinding.image2.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red_200
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                getStartedBinding.image3.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.active
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
        }
    }
}