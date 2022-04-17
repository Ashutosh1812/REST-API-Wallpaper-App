package com.ashutosh.wallpaperapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class MainApp:Application() {
    @Inject
    lateinit var switchTheme: SwitchTheme

    override fun onCreate() {
        super.onCreate()
        switchTheme.init()
    }

}