package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = ApiService.getInstance()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getWallpapers()
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful){
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, "$wallpaperPageModel", Toast.LENGTH_SHORT).show()
                    val list = wallpaperPageModel!!.data
                }
            }
        }
    }
}