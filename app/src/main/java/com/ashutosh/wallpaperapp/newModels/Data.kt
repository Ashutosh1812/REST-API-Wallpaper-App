package com.ashutosh.wallpaperapp.newModels


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("author")
    val author: Author,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("coins")
    val coins: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("colors")
    val colors: List<String>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("flip")
    val flip: Any,
    @SerializedName("license")
    val license: String,
    @SerializedName("rotation")
    val rotation: Int,
    @SerializedName("source")
    val source: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("wall_id")
    val wallId: Int
)