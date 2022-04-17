package com.ashutosh.wallpaperapp.network


import android.content.Context
import androidx.core.content.ContextCompat
import com.ashutosh.wallpaperapp.BuildConfig
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.models.WallListRequestModel
import com.ashutosh.wallpaperapp.models.WallpaperPageModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File


interface ApiService {


    companion object {
        fun getInstance(context: Context): ApiService {
            val cacheDir = File(context.cacheDir, "my-response-cache")
            val cache = Cache(cacheDir, 10 * 1024 * 1024)
            val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor { chain ->
                val original: Request = chain.request()

                // Request customization: add request headers

                val request: Request = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    .header("Accept", "application/json")

                    .build()
                chain.proceed(request)
            }.build()



            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
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
        @Query("order_by") orderBy: String? = null,
        @Query("s") search: String? = null,
        @Query("category") category: String? = null,
        @Query("color") color: String? = null,
    ): Response<WallpaperPageModel>

    @POST("wall/list")
    suspend fun getWallpapersByList(
        @Body body: WallListRequestModel
    ):Response<WallpaperPageModel>

    @GET("list/category")
    suspend fun getCategory(
    ): Response<List<CategoryModel>>
    @GET("list/color")
    suspend fun getColor(
    ): Response<List<ColorModel>>

}