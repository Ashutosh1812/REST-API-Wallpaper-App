package com.ashutosh.wallpaperapp.ui

import android.app.Activity
import android.app.DownloadManager
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityFullScreenBinding
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
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

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.parseColor("#01ffffff")

        val model = intent.getSerializableExtra("wall") as? WallpaperModel
        val data: Uri? = intent?.data
        if (model == null && data == null) {
            Toast.makeText(this, "Wallpaper Not Provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else if (model != null) {
            wallModel = model
            updateUI()
        } else
            fetchWallpaper(data!!)


//        bottomSheet()
        setWallpaperButton()
        downloadWallpaper()
//        applyBlurView(binding.blurryView,0.5f)
    }

    private fun fetchWallpaper(data: Uri) {

    }


    private fun downloadWallpaper(){
        binding.btnDownload.setOnClickListener{
            val url = wallModel.urls.full
            val request = DownloadManager.Request(Uri.parse(url))
            request.setDescription("wallModel.author.")
            request.setTitle("Wallpaper_${wallModel.wallId}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "Wall.png"
            )

            val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }
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

   /* private fun bottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }*/

    private fun setWallpaperButton() {
        binding.btnSetWallpaper.setOnClickListener {
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            binding.imageView.isDrawingCacheEnabled = true
//        Bitmap bitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
            val bitmap:Bitmap = binding.imageView.drawingCache
            CoroutineScope(Dispatchers.IO).launch {
                wallpaperManager.setBitmap(bitmap)

            }
            Toast.makeText(this, "Wallpaper Change Successfully", Toast.LENGTH_SHORT).show()

        }




    }




    fun Activity.applyBlurView(blurView: BlurView, radius:Float){
        val decorView:View = window.decorView
        val windowBackground: Drawable = decorView.background

        blurView.setupWith(decorView.findViewById(android.R.id.content))
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
        blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        blurView.clipToOutline = true

    }





}