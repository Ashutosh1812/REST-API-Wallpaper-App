package com.ashutosh.wallpaperapp.models

import com.google.gson.annotations.Expose

data class CategoryModel(
    val name: String,
    val popularity: Int,
    val imageUrl: String,

    )