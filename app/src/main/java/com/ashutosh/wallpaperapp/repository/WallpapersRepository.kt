package com.ashutosh.wallpaperapp.repository

import android.content.Context
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import com.ashutosh.wallpaperapp.network.ApiService
import com.ashutosh.wallpaperapp.room.FavDao
import com.ashutosh.wallpaperapp.room.FavEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class WallpapersRepository @Inject constructor(
    private val apiService: ApiService,
    private val favDao: FavDao
) {
    //    private val apiService = ApiService.getInstance(context = context)


    suspend fun getWallpapers(
        page: Int = 1, orderBy: String = "newest",
        search: String? = null,
        category: String? = null,
        color: String? = null,
    ): Response<WallpaperPageModel> {

        val response = apiService.getWallpapers(page, orderBy, search, category, color)
        if (response.isSuccessful){
            if (response.body() != null){
                for ((index, model) in response.body()!!.data.withIndex()){
                    if (favDao.getFav(model.wallId) != null)
                        response.body()!!.data[index].isFav = true
                }
            }
        }
        return response
    }

    suspend fun addToFav(wallId: Int) {
        favDao.insert(FavEntity(wallId))
    }

    suspend fun removeToFav(wallId: Int) {
        favDao.delete(FavEntity(wallId))
    }


}