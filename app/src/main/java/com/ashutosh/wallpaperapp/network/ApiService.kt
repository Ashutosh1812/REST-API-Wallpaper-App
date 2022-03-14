package com.ashutosh.wallpaperapp.network


import com.ashutosh.wallpaperapp.models.CategoriesModel
import com.ashutosh.wallpaperapp.models.CategoriesModelItem
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient

import okhttp3.Request


interface ApiService {

    companion object {
        fun getInstance(): ApiService {
            val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
                val original: Request = chain.request()

                // Request customization: add request headers
                val request: Request = original.newBuilder()
                    .header("Authorization", "Bearer ${Config.apiKey}")
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }.build()


            val retrofit = Retrofit.Builder()
                .baseUrl(Config.baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }


    @GET("wall")
    suspend fun getWallpapers(
        @Query("page") page: Int = 1
    ): Response<WallpaperPageModel>

    @GET("wall")
    suspend fun searchWallpapers(
        @Query("s") search: String,
        @Query("page") page: Int = 1
    ): Response<WallpaperPageModel>

    @GET("{category}/wall")
    suspend fun getWallByCategory(
        @Path(value = "category", encoded = true) category: String,
        @Query("page") page: Int = 1
    ): Response<WallpaperPageModel>

    @GET("{category}/wall")
    suspend fun searchWallByCategory(
        @Path(value = "category", encoded = true) category: String,
        @Query("s") search: String,
        @Query("page") page: Int = 1
    ): Response<WallpaperPageModel>


    @GET("list/category")
    suspend fun getCategory(
    ): Response<List<CategoriesModelItem>>

}