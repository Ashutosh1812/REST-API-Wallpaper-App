package com.ashutosh.wallpaperapp.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Author(
    @SerializedName("author_id")
    val authorId: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user_name")
    val userName: String
):Serializable