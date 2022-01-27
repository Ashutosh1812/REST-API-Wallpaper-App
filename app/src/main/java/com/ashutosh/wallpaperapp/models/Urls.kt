package com.ashutosh.wallpaperapp.models


import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("full")
    val full: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("raw")
    val raw: String? = null,
    @SerializedName("regular")
    val regular: String? = null
)