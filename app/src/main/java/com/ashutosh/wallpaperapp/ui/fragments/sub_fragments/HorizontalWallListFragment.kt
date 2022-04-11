package com.ashutosh.wallpaperapp.ui.fragments.sub_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.adapter.WallpapersAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentHorizontalWallListBinding
import com.ashutosh.wallpaperapp.ui.FullScreenActivity
import com.ashutosh.wallpaperapp.utils.BounceEdgeEffectFactory
import com.ashutosh.wallpaperapp.viewmodel.HorizontalWallListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorizontalWallListFragment : Fragment() {
    private var orderBy: String = ""
    private var page: Int = 1

    private var category: String? = null
    private lateinit var binding: FragmentHorizontalWallListBinding
    private val hwlViewModel: HorizontalWallListViewModel by viewModels()
    lateinit var adapter: WallpapersAdapter
    lateinit var layout: RecyclerView.LayoutManager
    var isScrolling: Boolean = false
    private val TAG = "Horizontal"
    var totalItem: Int? = null
    private var currentItem: Int? = null
    private var scrollOutItem: Int? = null
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
        hwlViewModel.getWallpaper(page, orderBy, category)
//        hwlViewModel.getWallpaper(2, orderBy, category)

        setupRecyclerView()
        hwlViewModel.liveIsLoading.observe(viewLifecycleOwner) {
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

    companion object {
        private const val ARG_PARAM1 = "param1"

        fun instance(orderBy: String, category: String? = null): HorizontalWallListFragment {
            return HorizontalWallListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, orderBy)
                    putString("category", category)
                }

            }

        }
    }

    private fun setupRecyclerView() {
        adapter = WallpapersAdapter(requireContext().applicationContext, hwlViewModel.list) {
            /*Intent(requireContext(),FullScreenActivity::class.java).apply {
                putExtra("wall",it)
            }.also{
                startActivity(it)
            }*/
            startActivity(Intent(requireContext(), FullScreenActivity::class.java).apply {
                putExtra("wall", it)
            })
        }
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = this@HorizontalWallListFragment.adapter
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory(true)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {



            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                currentItem = manager.findFirstVisibleItemPosition()
                totalItem = manager.itemCount
                scrollOutItem = manager.findFirstVisibleItemPosition()


//                hwlViewModel.getWallpaper(2, orderBy, category)


                if (isScrolling && (currentItem!! + scrollOutItem!! == totalItem)) {

                    //fetch data
                    isScrolling = false


//                    hwlViewModel.getWallpaper(page++, orderBy, category)
//                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        context, "OnScrolled" + page++ , Toast.LENGTH_SHORT
                    ).show()

                }


            }
        })

    }
}