package com.ashutosh.wallpaperapp.repository

import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class CategoryColorRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getCategory(): Response<List<CategoryModel>> {
        return apiService.getCategory()
    }suspend fun getColor(): Response<List<ColorModel>> {
        return apiService.getColor()
    }
}