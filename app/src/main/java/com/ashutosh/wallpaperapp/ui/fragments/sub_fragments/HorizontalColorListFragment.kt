package com.ashutosh.wallpaperapp.ui.fragments.sub_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashutosh.wallpaperapp.adapter.CategoriesAdapter
import com.ashutosh.wallpaperapp.adapter.ColorAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentHorizontalColorListBinding
import com.ashutosh.wallpaperapp.network.ListStatus
import com.ashutosh.wallpaperapp.viewmodel.HorizontalColorListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorizontalColorListFragment : Fragment() {
    private lateinit var binding: FragmentHorizontalColorListBinding
    private val hclViewModel: HorizontalColorListViewModel by viewModels()
    lateinit var adapter: ColorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorizontalColorListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        hclViewModel.listObserver.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.status) {
                ListStatus.INITIAL_LOADING -> {
//                    todo do something if wanted
                }
                ListStatus.INSERTED -> {
                    adapter.notifyItemRangeInserted(it.positionStart, it.itemCount)
                }
                else -> {
                }
            }
        }
        hclViewModel.getColor()
    }

    private fun setupRecyclerView() {
        adapter = ColorAdapter(requireContext(), hclViewModel.list)

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = this@HorizontalColorListFragment.adapter
        }
    }
}

//fragment_horizontal_category_list