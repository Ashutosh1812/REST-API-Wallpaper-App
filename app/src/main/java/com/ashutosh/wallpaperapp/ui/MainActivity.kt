package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.adapter.WallpapersAdapter
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

       lateinit var adapter: WallpapersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        homeViewModel.liveIsLoading.observe(this) {
            Toast.makeText(this, "${homeViewModel.list}", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }

        homeViewModel.getWallpaper()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = WallpapersAdapter(this, homeViewModel.list)
        binding.recyclerView.adapter = adapter
    }
}