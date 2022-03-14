package com.ashutosh.wallpaperapp.repository

import com.ashutosh.wallpaperapp.models.CategoriesModel
import com.ashutosh.wallpaperapp.models.CategoriesModelItem
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import com.ashutosh.wallpaperapp.network.ApiService
import retrofit2.Response

class WallpapersRepository {
    private val apiService = ApiService.getInstance()
    suspend fun getWallpapers():Response<WallpaperPageModel>{
        return apiService.getWallpapers()
    }
    suspend fun getCategory(): Response<List<CategoriesModelItem>> {
        return apiService.getCategory()
    }

}