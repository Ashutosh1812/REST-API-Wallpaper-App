package com.ashutosh.wallpaperapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.adapter.VerticalWallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.ActivityWallpapersBinding
import com.ashutosh.wallpaperapp.utils.BounceEdgeEffectFactory
import com.ashutosh.wallpaperapp.viewmodel.VerticalWallListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpapersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpapersBinding
    private var orderBy: String = "newest"
    private val hwlViewModel: VerticalWallListViewModel by viewModels()
    var model: String? = null
    var isLoading = true
    var isScrolling: Boolean = false
    private val TAG = "Horizontal"

    private lateinit var verticalWallpapersAdapter: VerticalWallpapersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpapersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = intent.getStringExtra("wall")
        binding.toolbar.setNavigationOnClickListener{
            finish()
        }
        binding.toolbarTitle.text = model
        setupRecyclerView()
        fetchWallpapers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchWallpapers() {
        hwlViewModel.getWallpaper(orderBy, model, model)

        hwlViewModel.liveIsLoading.observe(this) {

            verticalWallpapersAdapter.notifyDataSetChanged()

            when (it) {
                null -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
                    Log.d("TAG", "fun: ${hwlViewModel.list}")
                    verticalWallpapersAdapter.notifyDataSetChanged()

                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        }



    }

    private fun setupRecyclerView() {
        verticalWallpapersAdapter = VerticalWallpapersAdapter(this, hwlViewModel.list){
            startActivity(Intent(this, FullScreenActivity::class.java).apply {
                putExtra("wall", it)
            })
        }

        val manager = GridLayoutManager(applicationContext,3)
//        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = this@WallpapersActivity.verticalWallpapersAdapter



        var totalItem: Int
        var currentItem: Int
       var scrollOutItem: Int

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            currentItem = manager.childCount
            totalItem = manager.itemCount
            scrollOutItem = manager.findFirstVisibleItemPosition()

            if (isScrolling && currentItem + scrollOutItem == totalItem - 3) {

                hwlViewModel.getWallpaper(orderBy, model, model)
                //fetch data
                isScrolling = false


            }


        }
    })


        /*binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val totalItemCount: Int = manager.itemCount
                    val visibleItemCount: Int = manager.childCount
                    val pastVisibleItems: Int = manager.findFirstVisibleItemPosition()

                    if (isLoading && visibleItemCount + pastVisibleItems >= totalItemCount) {
                        Log.d("sdffsffsfsf","CallBack")
                        hwlViewModel.getWallpaperColor(model)
                        Toast.makeText(this@WallpapersActivity, "Call" + totalItemCount, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })*/




    }
}