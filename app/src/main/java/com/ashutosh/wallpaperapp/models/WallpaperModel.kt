package com.ashutosh.wallpaperapp.models


import com.google.gson.annotations.SerializedName

data class WallpaperModel(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("coins")
    val coins: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("license")
    val license: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls
)