package com.ashutosh.wallpaperapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ashutosh.wallpaperapp.databinding.FragmentHomeBinding
import com.ashutosh.wallpaperapp.ui.fragments.sub_fragments.HorizontalCategoryListFragment
import com.ashutosh.wallpaperapp.ui.fragments.sub_fragments.HorizontalColorListFragment
import com.ashutosh.wallpaperapp.ui.fragments.sub_fragments.HorizontalWallListFragment


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFrags(binding.container1.id, HorizontalWallListFragment.instance("newest"))
        initFrags(binding.container2.id, HorizontalWallListFragment.instance("newest", "Nature"))
        initFrags(binding.categoryContainer.id, HorizontalCategoryListFragment())
        initFrags(binding.colorContainer.id, HorizontalColorListFragment())

    }

    private fun initFrags(id: Int, f: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(id, f)
            .commit()
    }
}