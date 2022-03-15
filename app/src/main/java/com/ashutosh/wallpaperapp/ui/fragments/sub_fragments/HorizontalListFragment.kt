package com.ashutosh.wallpaperapp.ui.fragments.sub_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashutosh.wallpaperapp.R

class HorizontalListFragment() : Fragment() {
    private var orderBy: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderBy = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horizontal_list, container, false)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        fun newInstance(orderBy: String): HorizontalListFragment {
            return HorizontalListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, orderBy)
                }
            }
        }
    }
}