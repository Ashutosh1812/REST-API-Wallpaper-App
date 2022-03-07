package com.ashutosh.wallpaperapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.adapter.WallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentHomeBinding
import com.ashutosh.wallpaperapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel = HomeViewModel()

    lateinit var adapter: WallpapersAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.liveIsLoading.observe(this) {
//            Toast.makeText(this, "${homeViewModel.list}", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
            when (it) {
                null -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
//                    Log.d("TAG", "onCreate: ${homeViewModel.imagesDataList}")
                    Toast.makeText(context, "Notify", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
        homeViewModel.getWallpaper()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {


        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = manager
        adapter = WallpapersAdapter(requireContext().applicationContext, homeViewModel.list)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (manager.childCount + manager.findFirstVisibleItemPosition() == manager.itemCount) {

                    Log.d("TAG", "onScrolled: ${manager.childCount} ")
                    Toast.makeText(context, "${manager.childCount}", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }
}