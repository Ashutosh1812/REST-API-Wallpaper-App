package com.ashutosh.wallpaperapp.viewmodel

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.repository.CategoryColorRepository
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    private val wallpapersRepository: WallpapersRepository,
    private val categoryColorRepository: CategoryColorRepository
) : ViewModel() {


    private val _liveIsLoading: MutableLiveData<Boolean> = MutableLiveData(null)
    val liveIsLoading :LiveData<Boolean> = _liveIsLoading
//    private val _listObserver = MutableLiveData<Boolean>()
//    val listObserver: LiveData<Boolean> = _listObserver
private fun updateUI(context: Context, wallModel: WallpaperModel,imageView: ImageView) {


}


     fun setWall(context: Context, imageView: ImageView, progressBar: ProgressBar) {


    }


}