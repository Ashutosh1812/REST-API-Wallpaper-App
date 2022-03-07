package com.ashutosh.wallpaperapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityMainBinding
import com.ashutosh.wallpaperapp.ui.fragments.CategoryFragment
import com.ashutosh.wallpaperapp.ui.fragments.FavoritesFragment
import com.ashutosh.wallpaperapp.ui.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val fragement = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openMainFragment()
//        supportActionBar?.hide()

        setupFragments()
    }


    private fun setupFragments(){

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
            when(it){

                R.id.home -> {
                    openMainFragment()
                }
                R.id.category -> {
                    val categoryFragment = CategoryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, categoryFragment).commit()

                }
                R.id.favorites -> {
                    val favoriteFragment = FavoritesFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, favoriteFragment).commit()

                }
                R.id.settings -> {
                    val profileFragment = CategoryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, profileFragment).commit()
                    }
            }

            }
        }

    private fun openMainFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragement)
        transaction.commit()
    }

    }


