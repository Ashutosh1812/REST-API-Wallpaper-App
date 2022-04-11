package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ashutosh.wallpaperapp.adapter.VerticalWallpapersAdapter
import com.ashutosh.wallpaperapp.adapter.WallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.ActivityWallpapersBinding
import com.ashutosh.wallpaperapp.utils.BounceEdgeEffectFactory
import com.ashutosh.wallpaperapp.viewmodel.HorizontalWallListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WallpapersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpapersBinding
    private var orderBy: String = "newest"
    private var page: Int = 1
    private val hwlViewModel: HorizontalWallListViewModel by viewModels()


    lateinit var adapter: VerticalWallpapersAdapter

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
        Toast.makeText(
            this,
            "" + hwlViewModel.getWallpaper(page, orderBy, categoryName),
            Toast.LENGTH_SHORT
        ).show()

//        adapter.notifyDataSetChanged()
        hwlViewModel.liveIsLoading.observe(this) {
//            Toast.makeText(this, "${homeViewModel.list}", Toast.LENGTH_SHORT).show()

            adapter.notifyDataSetChanged()

            when (it) {
                null -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
//                    Log.d("TAG", "onCreate: ${homeViewModel.imagesDataList}")
//                    Toast.makeText(context, "Notify", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()

//                    binding.progressBar.visibility = View.GONE
                }
                else -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory(true)

        binding.recyclerView.apply {
            layoutManager =
                GridLayoutManager(context, 2)
            adapter = this.adapter
        }
    }
}