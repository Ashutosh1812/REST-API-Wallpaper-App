package com.ashutosh.wallpaperapp.repository

import android.content.Context
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import com.ashutosh.wallpaperapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class WallpapersRepository @Inject constructor(private val apiService: ApiService) {
//    private val apiService = ApiService.getInstance(context = context)
    suspend fun getWallpapers(page: Int = 1, orderBy:String = "newest",
                              search: String? = null,
                              category: String? = null,
                              color: String? = null,):Response<WallpaperPageModel>{
        return apiService.getWallpapers(page, orderBy, search, category, color)
    }


}