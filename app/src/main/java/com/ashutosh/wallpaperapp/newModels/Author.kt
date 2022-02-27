package com.ashutosh.wallpaperapp.newModels


import com.google.gson.annotations.SerializedName

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
)