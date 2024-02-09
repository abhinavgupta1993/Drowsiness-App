package com.drowsiness.ai.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityGetStartedBinding
import com.drowsiness.ai.databinding.ActivitySplashBinding

class GetStartedActivity : AppCompatActivity() {

    lateinit var getStartedBinding: ActivityGetStartedBinding
    lateinit var iv1:ImageView
    lateinit var iv2:ImageView
    lateinit var iv3:ImageView
    lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getStartedBinding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(getStartedBinding.root)
        getStartedBinding.button.setOnClickListener {
//            var intent = Intent(this, SplashActivity::class.java)
            startActivity(Intent(this,MainActivity::class.java))
        }

        getStartedBinding.button2.setOnClickListener {
//            var intent = Intent(this, SplashActivity::class.java)
            startActivity(Intent(this,MainActivity::class.java))
        }



        viewPager2=findViewById(R.id.view_pager2)
        iv1=findViewById(R.id.iv1)
        iv2=findViewById(R.id.iv2)
        iv3=findViewById(R.id.iv3)

        val images= listOf(R.drawable.phone,R.drawable.images,R.drawable.cardriving)
        val adapter=ViewPagerAdapter(images)
        viewPager2.adapter=adapter

        viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
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
        when(viewPager2.currentItem){
            0->
            {
                iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.active,theme))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.red_200))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.red_200))
            }
            1->
            {
                iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.red_200))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.active))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.red_200))
            }
            2->
            {
                iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.red_200))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.red_200))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.active))
            }
        }
    }
}