package com.ashutosh.wallpaperapp.network


import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


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
        @Query("page") page: Int = 1,
        @Query("order_by") orderBy: String = "newest",
        @Query("s") search: String? = null,
        @Query("category") category: String? = null,
        @Query("color") color: String? = null,
    ): Response<WallpaperPageModel>

    @GET("list/category")
    suspend fun getCategory(
    ): Response<List<CategoryModel>>
    @GET("list/color")
    suspend fun getColor(
    ): Response<List<ColorModel>>

}