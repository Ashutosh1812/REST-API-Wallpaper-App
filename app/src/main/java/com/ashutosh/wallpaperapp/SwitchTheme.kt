package com.ashutosh.wallpaperapp

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SwitchTheme @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPref = context.getSharedPreferences("ThemePref", Context.MODE_PRIVATE)


    fun init() {
        AppCompatDelegate.setDefaultNightMode(getTheme())
    }

    fun setTheme(mode: Int) {
        if (mode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM || mode == AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY ||
            mode == AppCompatDelegate.MODE_NIGHT_YES || mode == AppCompatDelegate.MODE_NIGHT_NO
        ) {
            saveTheme(mode)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }



    private fun getTheme() = sharedPref.getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//    private fun getTheme() = sharedPref.getInt("mode", AppCompatDelegate.MODE_NIGHT_YES)
    private fun saveTheme(mode:Int){
        sharedPref.edit().putInt("mode", mode).apply()
    }
}