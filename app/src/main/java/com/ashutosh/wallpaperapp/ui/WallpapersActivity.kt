package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ashutosh.wallpaperapp.adapter.VerticalWallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.ActivityWallpapersBinding
import com.ashutosh.wallpaperapp.utils.BounceEdgeEffectFactory
import com.ashutosh.wallpaperapp.viewmodel.HorizontalWallListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpapersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpapersBinding
    private var orderBy: String = "newest"
    private var page: Int = 1
    private val hwlViewModel: HorizontalWallListViewModel by viewModels()


    private lateinit var verticalWallpapersAdapter: VerticalWallpapersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpapersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model = intent.getStringExtra("wall")

        binding.tv.text = model
        setupRecyclerView()
        fetchWallpapers(model!!)

    }


    private fun fetchWallpapers(categoryName: String) {
        hwlViewModel.getWallpaper(page, orderBy, categoryName)

//        adapter.notifyDataSetChanged()
        hwlViewModel.liveIsLoading.observe(this) {
//            Toast.makeText(this, "${homeViewModel.list}", Toast.LENGTH_SHORT).show()

            verticalWallpapersAdapter.notifyDataSetChanged()

            when (it) {
                null -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
                    Log.d("TAG", "fun: ${hwlViewModel.list}")
                    Toast.makeText(this, "Notify", Toast.LENGTH_SHORT).show()
                    verticalWallpapersAdapter.notifyDataSetChanged()

//                    binding.progressBar.visibility = View.GONE
                }
                else -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setupRecyclerView() {
        verticalWallpapersAdapter = VerticalWallpapersAdapter(this, hwlViewModel.list){

        }
        binding.recyclerView.apply {
            edgeEffectFactory = BounceEdgeEffectFactory()
            layoutManager =
                GridLayoutManager(context, 2)
            adapter = this@WallpapersActivity.verticalWallpapersAdapter
        }
    }
}