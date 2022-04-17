package com.ashutosh.wallpaperapp.models

import com.google.gson.annotations.SerializedName

data class WallListRequestModel(
    @SerializedName("list")
    val list: List<Int>
)
