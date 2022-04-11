package com.ashutosh.wallpaperapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private var page: Int = 1
    private val hwlViewModel: VerticalWallListViewModel by viewModels()
    var model: String? = null
    var isScrolling = false
    var totalItem: Int? = null
    private var currentItem: Int? = null
    private var scrollOutItem: Int? = null

    private lateinit var verticalWallpapersAdapter: VerticalWallpapersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpapersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = intent.getStringExtra("wall")

        binding.toolbarTitle.text = model
        setupRecyclerView()
        setUpPagination(true)
        fetchWallpapers()
//        hwlViewModel.getWallpaper(2, orderBy, model)

    }




    private fun setUpPagination(isPaginationAllowed: Boolean) {



    }


    @SuppressLint("NotifyDataSetChanged")
    private fun fetchWallpapers() {
        hwlViewModel.getWallpaper(page, orderBy, model)

        hwlViewModel.liveIsLoading.observe(this) {

            verticalWallpapersAdapter.notifyDataSetChanged()

            when (it) {
                null -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
                    Log.d("TAG", "fun: ${hwlViewModel.list}")

//                    Toast.makeText(this, "Notify"+hwlViewModel.list, Toast.LENGTH_LONG).show()
//                    binding.tv.text = hwlViewModel.list.toString()

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
            startActivity(Intent(this, FullScreenActivity::class.java).apply {
                putExtra("wall", it)
            })
        }

        val manager = GridLayoutManager(applicationContext,2)
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = this@WallpapersActivity.verticalWallpapersAdapter

        var loading = true
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = manager.childCount
                    totalItemCount = manager.itemCount
                    pastVisibleItems = manager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            Log.v("...", "Last Item Wow !")
                            // Do pagination.. i.e. fetch new data
                            page++
                            Toast.makeText(baseContext, ""+page, Toast.LENGTH_SHORT).show()
                            hwlViewModel.getWallpaper(2, orderBy, model)

                            loading = true
                        }
                    }
                }
            }
        })

    }
}