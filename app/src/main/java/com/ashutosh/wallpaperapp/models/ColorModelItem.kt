package com.ashutosh.wallpaperapp.models


import com.google.gson.annotations.SerializedName

data class ColorModelItem(
    @SerializedName("name")
    val name: String, // Yellow
    @SerializedName("popularity")
    val popularity: Int, // 0
    @SerializedName("value")
    val value: String // #ffeb3b
)