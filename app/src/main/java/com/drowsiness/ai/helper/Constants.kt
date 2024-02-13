package com.drowsiness.ai.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.provider.Settings
import android.util.Patterns
import androidx.core.content.res.ResourcesCompat
import com.drowsiness.ai.R

// name - Abhinav Gupta
// created at 13th Feb 2024

class Constants {

    companion object {
        fun customTypefaceNunitoRegular(context: Context): Typeface {
            return ResourcesCompat.getFont(context.applicationContext, R.font.n_bold)!!
        }

        fun isEmailValid(emailAddress: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
        }
        fun isNameLengthGreaterThan3(name: String): Boolean {
            return name.length >= 3
        }

        fun isPasswordEmpty(password: String): Boolean{
            return password.length >= 8
        }

        fun isPasswordMatch(password : String, confirmPassword: String): Boolean{
            return password.equals(confirmPassword)
        }

        @SuppressLint("HardwareIds")
        fun getDeviceID(context: Context): String {
            return Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
            )
        }

        fun showDialog(context: Context?, isDialog: Boolean) {
            if (isDialog) {
                if (context != null) {
                    ProgressDialog.start(context)
                }
            }
        }

        fun dismissDialog(isDialog: Boolean) {
            if (isDialog) {
                ProgressDialog.dismiss()
            }
        }

    }

}