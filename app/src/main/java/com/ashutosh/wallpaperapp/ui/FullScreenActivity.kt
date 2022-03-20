package com.ashutosh.wallpaperapp.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityFullScreenBinding
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@AndroidEntryPoint
class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenBinding

    @Inject
    lateinit var wallpapersRepository: WallpapersRepository
    private lateinit var wallModel: WallpaperModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getSerializableExtra("wall") as? WallpaperModel
        val data: Uri? = intent?.data
        if (model == null && data == null) {
            Toast.makeText(this, "Wallpaper Not Provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else if (model != null) {
            wallModel = model
            updateUI()
        }
        else
            fetchWallpaper(data!!)


    }

    private fun fetchWallpaper(data: Uri) {

    }

    private fun updateUI() {
        Glide.with(this).load(wallModel.urls.regular).into(binding.imageView)

        updateFavButton(wallModel.isFav)
        binding.favButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (wallModel.isFav) {
                    wallpapersRepository.removeToFav(wallModel.wallId)
                    wallModel.isFav = false
                } else {
                    wallpapersRepository.addToFav(wallModel.wallId)
                    wallModel.isFav = true
                }
                updateFavButton(wallModel.isFav)
            }

        }
    }

    private fun updateFavButton(isFav: Boolean) {

        binding.favButton.setImageResource(
            if (isFav)
                R.drawable.ic_baseline_favorite_24
            else
                R.drawable.ic_baseline_favorite_border_24
        )

    }
}