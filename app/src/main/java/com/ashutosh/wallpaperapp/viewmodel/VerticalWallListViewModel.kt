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
class VerticalWallListViewModel @Inject constructor(private val wallpapersRepository: WallpapersRepository) :
    ViewModel() {

    private val _list: ArrayList<WallpaperModel> = ArrayList()
    val list: List<WallpaperModel> = _list
    private val _liveIsLoading: MutableLiveData<Boolean> = MutableLiveData(null)
    val liveIsLoading: LiveData<Boolean> = _liveIsLoading
    var page: Int = 1
    var color: String? = null
    var category: String? = null
    var orderBy: String = "newest"

    fun init(orderBy: String, category: String? = null, color: String? = null) {
        this.orderBy = orderBy
        this.category = category
        this.color = color
    }

    fun getWallpaper() {
//        if (_list.isNotEmpty()) return
        _liveIsLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val response = wallpapersRepository.getWallpapers(
                page = page,
                orderBy = orderBy,
                category = category,
                color = color
            )
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful) {
                val lastPage = response.body()!!.lastPage
                if (page <= lastPage) {
                    page++
                }
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main) {
                    _list.addAll(wallpaperPageModel!!.data)
                    Log.d("Total Item In List", wallpaperPageModel.total.toString())
                    _liveIsLoading.value = false
                }
            }
        }
    }

    fun getFavWallpapers() {
        _liveIsLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                wallpapersRepository.getWallpapersByList(wallpapersRepository.getFavList())
            if (response.isSuccessful) {
                val lastPage = response.body()!!.lastPage
                if (page <= lastPage) {
                    page++
                }
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main) {
                    _list.addAll(wallpaperPageModel!!.data)
                    Log.d("Total Item In List", wallpaperPageModel.total.toString())
                    _liveIsLoading.value = false
                }
            }
        }
    }

    fun filterList() {


        _liveIsLoading.value = true
        viewModelScope.launch {
            _list.forEach {
                if (!wallpapersRepository.isFav(it.wallId))
                    _list.remove(it)
            }
            _liveIsLoading.value = false
        }
    }


}