package com.ashutosh.wallpaperapp.viewmodel

import android.util.Log
import android.widget.Toast
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
class VerticalWallListViewModel @Inject constructor(private val wallpapersRepository: WallpapersRepository) : ViewModel() {

    private val _list: ArrayList<WallpaperModel> = ArrayList()
    val list : List<WallpaperModel> = _list
    private val _liveIsLoading: MutableLiveData<Boolean> = MutableLiveData(null)
    val liveIsLoading :LiveData<Boolean> = _liveIsLoading
    private var page:Int = 1

    fun getWallpaper(orderBy:String, category: String?=null, color: String?=null) {
//        if (_list.isNotEmpty()) return
        _liveIsLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val response = wallpapersRepository.getWallpapers(page = page,orderBy = orderBy, category = category, color = color)
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful){
                val lastPage = response.body()!!.lastPage
                if (page <= lastPage){
                    page++
                }
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main){
                    _list.addAll(wallpaperPageModel!!.data)
                    Log.d("Total Item In List",wallpaperPageModel.total.toString())
                    _liveIsLoading.value = false
                }
            }
        }
    }


}