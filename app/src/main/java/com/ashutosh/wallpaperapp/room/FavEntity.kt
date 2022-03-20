package com.ashutosh.wallpaperapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favTable")
data class FavEntity(
    @PrimaryKey
    val wallId: Int
)