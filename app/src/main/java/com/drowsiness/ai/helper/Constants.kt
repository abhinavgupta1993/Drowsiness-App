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


// Common class
class Constants {

    companion object {
        fun customTypefaceNunitoRegular(context: Context): Typeface {
            return ResourcesCompat.getFont(context.applicationContext, R.font.n_bold)!!
        }

        // is email valid or not
        fun isEmailValid(emailAddress: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
        }

        // is name having at least 3 characters
        fun isNameLengthGreaterThan3(name: String): Boolean {
            return name.length >= 3
        }

        // is password empty or less than or equals to 8
        fun isPasswordEmpty(password: String): Boolean{
            return password.length >= 8
        }

        // is password and confirm password matches?
        fun isPasswordMatch(password : String, confirmPassword: String): Boolean{
            return password.equals(confirmPassword)
        }

        // getting device id
        @SuppressLint("HardwareIds")
        fun getDeviceID(context: Context): String {
//            here we fetched the device id and using the context
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID
            )
        }

        // showing ProgressDialog
        fun showDialog(context: Context?, isDialog: Boolean) {
            if (isDialog) {
                if (context != null) {
                    ProgressDialog.start(context)
                }
            }
        }

        // dismiss ProgressDialog
        fun dismissDialog(isDialog: Boolean) {
            if (isDialog) {
                ProgressDialog.dismiss()
            }
        }

    }

}