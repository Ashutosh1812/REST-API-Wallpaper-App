package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityMainBinding
import com.ashutosh.wallpaperapp.ui.fragments.CategoryFragment
import com.ashutosh.wallpaperapp.ui.fragments.FavoritesFragment
import com.ashutosh.wallpaperapp.ui.fragments.HomeFragment

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
        binding.menuBottom.setItemSelected(R.id.home)
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


