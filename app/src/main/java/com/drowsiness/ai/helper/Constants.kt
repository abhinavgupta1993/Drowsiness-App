package com.drowsiness.ai.helper

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.drowsiness.ai.R

class Constants {

    companion object{
        fun customTypefaceNunitoRegular(context: Context): Typeface {
            return ResourcesCompat.getFont(context.applicationContext, R.font.n_bold)!!
        }
    }

}