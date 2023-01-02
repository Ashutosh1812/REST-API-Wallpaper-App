package com.ashutosh.wallpaperapp.repository

import com.ashutosh.wallpaperapp.models.WallListRequestModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import com.ashutosh.wallpaperapp.network.ApiService
import com.ashutosh.wallpaperapp.room.FavDao
import com.ashutosh.wallpaperapp.room.FavEntity
import retrofit2.Response
import javax.inject.Inject

class WallpapersRepository @Inject constructor(
    private val apiService: ApiService,
    private val favDao: FavDao
) {
    //    private val apiService = ApiService.getInstance(context = context)


    suspend fun getWallpapers(
        page: Int = 1, orderBy: String? = "",
        search: String? = null,
        category: String? = null,
        color: String? = null,
    ): Response<WallpaperPageModel> {
        return apiService.getWallpapers(page = page, orderBy, search, category, color)
    }


    suspend fun getWallpapersByList(list: List<Int>): Response<WallpaperPageModel> {
        return apiService.getWallpapersByList(WallListRequestModel(list))
    }

    suspend fun addToFav(wallId: Int) {
        favDao.insert(FavEntity(wallId))
    }

    suspend fun removeToFav(wallId: Int) {
        favDao.delete(FavEntity(wallId))
    }

    suspend fun isFav(wallId: Int): Boolean {
        return favDao.getFav(wallId) != null
    }


}