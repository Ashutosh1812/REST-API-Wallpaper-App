package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.adapter.ViewPagerAdapter
import com.ashutosh.wallpaperapp.adapter.WallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.ActivityMainBinding
import com.ashutosh.wallpaperapp.network.ApiService
import com.ashutosh.wallpaperapp.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragments()
    }


    private fun setupFragments(){

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2){tab,position->
            when(position){
                0-> tab.text = "Home"
                1-> tab.text = "Category"
                2-> tab.text = "Favorite"
            }

        }.attach()

    }


}