package com.ashutosh.wallpaperapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ashutosh.wallpaperapp.adapter.CategoriesAdapter
import com.ashutosh.wallpaperapp.adapter.CategoriesHomeAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentCategoryBinding
import com.ashutosh.wallpaperapp.network.ListStatus
import com.ashutosh.wallpaperapp.viewmodel.HorizontalCategoryListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val hclViewModel: HorizontalCategoryListViewModel by viewModels()
    lateinit var categoriesAdapter: CategoriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
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
                }
                ListStatus.INSERTED -> {
                    categoriesAdapter.notifyItemRangeInserted(it.positionStart, it.itemCount)
                }
                else -> {
                }
            }
        }
        Toast.makeText(
            context, hclViewModel.getCategories().toString(), Toast.LENGTH_SHORT
        ).show()
        hclViewModel.getCategories()
    }


    private fun setupRecyclerView() {
        categoriesAdapter = CategoriesAdapter(requireContext(), hclViewModel.list)

        binding.categoriesFragmentRecyclerView.apply {
            layoutManager =
                GridLayoutManager(context, 3)
            adapter = this@CategoryFragment.categoriesAdapter
        }
    }
}