package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityMainBinding
import com.ashutosh.wallpaperapp.network.ApiService
import com.ashutosh.wallpaperapp.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val homeViewModel: HomeViewModel by viewModels()
//    lateinit var adapter: ImagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    homeViewModel.liveIsLoading.observe(this){
        Toast.makeText(this, "${homeViewModel.list}", Toast.LENGTH_SHORT).show()
        binding.textView.text = homeViewModel.list.toString()
    }

        homeViewModel.getWallpaper()
    }
}