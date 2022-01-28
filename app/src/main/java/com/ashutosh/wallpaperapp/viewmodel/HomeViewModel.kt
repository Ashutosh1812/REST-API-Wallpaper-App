package com.ashutosh.wallpaperapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    val list: ArrayList<WallpaperModel> = ArrayList()
    val liveIsLoading: MutableLiveData<Boolean> = MutableLiveData(null)

    private val wallpapersRepository = WallpapersRepository()

    fun getWallpaper() {
        if (list.isNotEmpty()) return
        liveIsLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = wallpapersRepository.getWallpapers()
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful){
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main){
                    list.addAll(wallpaperPageModel!!.data)
                    liveIsLoading.value = false
                }
            }
        }
    }
}