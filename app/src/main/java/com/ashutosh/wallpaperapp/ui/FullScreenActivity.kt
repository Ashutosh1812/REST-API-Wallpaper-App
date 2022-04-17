package com.ashutosh.wallpaperapp.ui

import android.R.attr.bitmap
import android.app.Activity
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ActivityFullScreenBinding
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import com.ashutosh.wallpaperapp.viewmodel.FullScreenViewModel
import com.ashutosh.wallpaperapp.viewmodel.VerticalWallListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


@AndroidEntryPoint
class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenBinding

    @Inject
    lateinit var wallpapersRepository: WallpapersRepository
    private val hwlViewModel: FullScreenViewModel by viewModels()
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

        hwlViewModel.wallModel.observe(this){
            it?.let {
                wallModel = it
                updateUI()
            }
        }


//        bottomSheet()
        setWallpaper()
        downloadWallpaper()
        backButton()
        showDialog()
//        shareWallpaper()
//        applyBlurView(binding.blurryView,0.5f)
    }

    private fun fetchWallpaper(data: Uri) {
        hwlViewModel.loadSharedWall(data)
    }


    private fun showDialog() {
        binding.moreButton.setOnClickListener {

            val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .create()
            val view = layoutInflater.inflate(R.layout.more_dialog_layout, null)
//            val textViewSource = view.findViewById<TextView>(R.id.textSource)
            val textViewSourceUrl = view.findViewById<TextView>(R.id.textSourceUrl)
            val textViewAuthor = view.findViewById<TextView>(R.id.textAuthorName)
            val textViewDownload = view.findViewById<TextView>(R.id.textDownload)
            val textViewLicense = view.findViewById<TextView>(R.id.textLicense)
            val authorImage = view.findViewById<ImageView>(R.id.authorImage)
            val shareButton = view.findViewById<ImageButton>(R.id.shareButton)
            builder.setView(view)

            shareButton.setOnClickListener {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, wallModel.urls.small)
                sharingIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Wallpaper App \n http://wallpaper.onetakego.com/id/${wallModel.wallId}"
                )
                startActivity(Intent.createChooser(sharingIntent, "Share via"))
            }


            textViewSourceUrl.text = wallModel.source
            textViewAuthor.text = wallModel.author.name
            textViewDownload.text = "Downloads- ${wallModel.downloads.toString()}"
            textViewLicense.text = wallModel.license
            val cardRadius = this.resources.getDimension(R.dimen.card_corner_radius)

            val mlt = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    cardRadius.roundToInt(),
                    0
                )
            )

            Glide.with(this).load(wallModel.author.image)
                .transform(mlt)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(authorImage)

            builder.setCanceledOnTouchOutside(true)
            builder.show()

        }


    }


    private fun backButton() {

        binding.backButton.setOnClickListener {
            finish()
        }

    }


    private fun downloadWallpaper() {
        binding.downloadButton.setOnClickListener {
            val url = wallModel.urls.raw
            val request = DownloadManager.Request(Uri.parse(url))
//            request.setDescription("wallModel.author.")
            request.setTitle("Wallpaper_${wallModel.wallId}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "Wallpaper${wallModel.wallId}.png"
            )

            val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }
    }


    private fun updateUI() {


        Glide.with(this).load(wallModel.urls.full).into(binding.imageView)


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

    private fun setWallpaper() {


        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.wallpaper_layout, null)

        val homeButton = view.findViewById<Button>(R.id.homeScreen)
        val lockButton = view.findViewById<Button>(R.id.lockScreen)
        val bothButton = view.findViewById<Button>(R.id.bothScreen)

        builder.setView(view)
        binding.setWallpaperButton.setOnClickListener {
//            fullScreenViewModel.setWall(this,binding.imageView,binding.progressBar)
            val wallpaperManager = WallpaperManager.getInstance(this)
            binding.imageView.isDrawingCacheEnabled = true
//        Bitmap bitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
            val bitmap: Bitmap = binding.imageView.drawingCache
            homeButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    CoroutineScope(Dispatchers.IO).launch {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )
                        builder.dismiss()
                        withContext(Dispatchers.Main) {
//                    progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@FullScreenActivity,
                                "Wallpaper Change Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }

            lockButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    CoroutineScope(Dispatchers.IO).launch {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            false,
                            WallpaperManager.FLAG_LOCK
                        )
                        builder.dismiss()
                        withContext(Dispatchers.Main) {
//                    progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@FullScreenActivity,
                                "Wallpaper Change Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }

//            bothButton.visibility = View.GONE
            bothButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            false,
                        )
                        withContext(Dispatchers.Main) {
//                    progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@FullScreenActivity,
                                "Wallpaper Change Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    } else {
                        wallpaperManager.setBitmap(bitmap)
                    }
                }
            }

            builder.setCanceledOnTouchOutside(true)
            builder.show()
            //            progressBar.visibility = View.VISIBLE


        }
    }
    fun Activity.applyBlurView(blurView: BlurView, radius: Float) {
        val decorView: View = window.decorView
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