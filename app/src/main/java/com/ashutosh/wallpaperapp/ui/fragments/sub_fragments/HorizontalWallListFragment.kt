package com.ashutosh.wallpaperapp.ui.fragments.sub_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashutosh.wallpaperapp.adapter.WallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentHorizontalListBinding
import com.ashutosh.wallpaperapp.databinding.FragmentHorizontalWallListBinding
import com.ashutosh.wallpaperapp.viewmodel.HorizontalWallListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorizontalWallListFragment : Fragment() {
    private var orderBy: String = ""
    private var category: String? = null
    private lateinit var binding: FragmentHorizontalWallListBinding
    private val hwlViewModel: HorizontalWallListViewModel by viewModels()
    lateinit var adapter: WallpapersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderBy = it.getString(ARG_PARAM1).toString()
            category = it.getString("category")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorizontalWallListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        hwlViewModel.getWallpaper(orderBy, category)
        hwlViewModel.liveIsLoading.observe(viewLifecycleOwner) {
//            Toast.makeText(this, "${homeViewModel.list}", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
            when (it) {
                null -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> {
//                    Log.d("TAG", "onCreate: ${homeViewModel.imagesDataList}")
                    Toast.makeText(context, "Notify", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
//                    binding.progressBar.visibility = View.GONE
                }
                else -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        fun instance(orderBy: String, category: String?= null): HorizontalWallListFragment {
            return HorizontalWallListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, orderBy)
                    putString("category", category)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = WallpapersAdapter(requireContext().applicationContext, hwlViewModel.list)

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = this@HorizontalWallListFragment.adapter
        }
    }
}