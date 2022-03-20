package com.ashutosh.wallpaperapp.ui

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityMainBinding
import com.ashutosh.wallpaperapp.ui.fragments.CategoryFragment
import com.ashutosh.wallpaperapp.ui.fragments.FavoritesFragment
import com.ashutosh.wallpaperapp.ui.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var fragement : HomeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragement = HomeFragment()
        switchFragment(fragement)
//        supportActionBar?.hide()

        setupFragments()
    }


    private fun setupFragments() {

        /*val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager2){tab,position->
            when(position){
                0-> tab.text = "Home"
                1-> tab.text = "Category"
                2-> tab.text = "Favorite"
            }

        }.attach()*/
        val resources: Resources = this.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        binding.menuBottom.setItemSelected(R.id.home)

        Toast.makeText(this, resources.getDimensionPixelSize(resourceId).toString()+ "fhgfhghgg", Toast.LENGTH_SHORT).show()
        binding.menuBottom.setOnItemSelectedListener {
            when (it) {

                R.id.home -> {
                    switchFragment(fragement)
                }
                R.id.category -> {
                    switchFragment(CategoryFragment())

                }
                R.id.favorites -> {
                    switchFragment(FavoritesFragment())

                }
                R.id.settings -> {
                    switchFragment(CategoryFragment())
                }
            }

        }
    }
    private fun switchFragment(f:Fragment?){
        f?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, it).commit()
        }
    }

}


