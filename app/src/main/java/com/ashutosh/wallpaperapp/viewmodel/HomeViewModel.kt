package com.ashutosh.wallpaperapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val wallpapersRepository: WallpapersRepository) : ViewModel() {

    val list: ArrayList<WallpaperModel> = ArrayList()
    private val categoryList:ArrayList<CategoryModel> = ArrayList()

    private val _liveIsLoading: MutableLiveData<Boolean> = MutableLiveData(null)
    val liveIsLoading :LiveData<Boolean> = _liveIsLoading

    fun getWallpaper() {
        if (list.isNotEmpty()) return
        _liveIsLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val response = wallpapersRepository.getWallpapers()
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful){
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main){
                    list.addAll(wallpaperPageModel!!.data)
                    _liveIsLoading.value = false
                }
            }
        }
    }

    fun getCategory(){
        if (categoryList.isNotEmpty()) return
        _liveIsLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val response = wallpapersRepository.getCategory()
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful){
                val categoryModel = response.body()
                withContext(Dispatchers.Main){
                    Log.d("TAGccc", "onCreate: $categoryModel")



                }
            }
        }
    }


}