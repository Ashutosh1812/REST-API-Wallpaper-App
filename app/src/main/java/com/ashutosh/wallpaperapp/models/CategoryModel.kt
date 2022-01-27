package com.ashutosh.wallpaperapp.models


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("preview_urls")
    val previewUrls: List<String>
)