package com.ashutosh.wallpaperapp.repository

import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import com.ashutosh.wallpaperapp.network.ApiService
import retrofit2.Response

class WallpapersRepository {
    private val apiService = ApiService.getInstance()
    suspend fun getWallpapers(page: Int = 1, orderBy:String = "newest",
                              search: String? = null,
                              category: String? = null,
                              color: String? = null,):Response<WallpaperPageModel>{
        return apiService.getWallpapers(page, orderBy, search, category, color)
    }
    suspend fun getCategory(): Response<List<CategoryModel>> {
        return apiService.getCategory()
    }suspend fun getColor(): Response<List<ColorModel>> {
        return apiService.getColor()
    }

}