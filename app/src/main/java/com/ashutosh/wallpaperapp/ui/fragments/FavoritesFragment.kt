package com.ashutosh.wallpaperapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.adapter.VerticalWallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentFavoritesBinding
import com.ashutosh.wallpaperapp.ui.FullScreenActivity
import com.ashutosh.wallpaperapp.viewmodel.VerticalWallListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var verticalWallpapersAdapter: VerticalWallpapersAdapter
    private val viewModel: VerticalWallListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchWallpapers()
    }

    private fun fetchWallpapers() {
        viewModel.getFavWallpapers()

        viewModel.liveIsLoading.observe(viewLifecycleOwner) {

            verticalWallpapersAdapter.notifyDataSetChanged()
//            verticalWallpapersAdapter.notifyItemRangeInserted(currentItem,totalItem)
//            verticalWallpapersAdapter.notifyItemRangeChanged()
//            Toast.makeText(this, "" + viewModel.list.size, Toast.LENGTH_SHORT).show()

            when (it) {
                null -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
                    Log.d("TAG", "fun: ${viewModel.list}")
                    verticalWallpapersAdapter.notifyDataSetChanged()
//                    verticalWallpapersAdapter.notifyItemRangeInserted(currentItem ,totalItem)

//                    binding.progressBar.visibility = View.GONE
                }
                else -> {
//                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        }


    }



    private fun setupRecyclerView() {
        verticalWallpapersAdapter = VerticalWallpapersAdapter(requireContext(), viewModel.list) {
            startActivity(Intent(requireContext(), FullScreenActivity::class.java).apply {
                putExtra("wall", it)
            })
        }

        val manager = GridLayoutManager(requireContext(), 3)
//        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = verticalWallpapersAdapter

    }

}