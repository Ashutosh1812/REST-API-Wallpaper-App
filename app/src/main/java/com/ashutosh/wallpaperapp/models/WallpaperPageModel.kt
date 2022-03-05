package com.ashutosh.wallpaperapp.models


import com.ashutosh.wallpaperapp.newModels.Data
import com.google.gson.annotations.SerializedName
import kotlin.math.ceil

data class WallpaperPageModel(

    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("data")
    val `data`: List<WallpaperModel>,
    @SerializedName("from")
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("to")
    val to: Int,
    @SerializedName("total")
    val total: Int


){
    val pagesCount:Int = ceil(total/(perPage).toFloat()).toInt()
}