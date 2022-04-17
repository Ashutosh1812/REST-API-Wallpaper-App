package com.ashutosh.wallpaperapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    private val wallpapersRepository: WallpapersRepository
) : ViewModel() {


    private val _wallModel: MutableLiveData<WallpaperModel> = MutableLiveData(null)
    val wallModel: LiveData<WallpaperModel> = _wallModel
    fun loadSharedWall(data: Uri) {
        val id = data.lastPathSegment?.toIntOrNull() ?: return

        Log.d("tags", "$id i ")
        viewModelScope.launch(Dispatchers.IO) {
            val response = wallpapersRepository.getWallpapersByList(listOf(id))
            if (response.isSuccessful && response.body() != null){
                val wallpaperPageModel = response.body()!!
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main){
                    _wallModel.value = wallpaperPageModel.data[0]
                }
            }
        }
    }


}