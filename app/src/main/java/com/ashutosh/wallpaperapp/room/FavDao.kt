package com.ashutosh.wallpaperapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavDao {
//    @Query("SELECT * From favTable where wallId = :id  ")
//    fun getFav(id:Int):FavEntity?

    @Query("SELECT * From favTable")
    fun getAllFav(): List<FavEntity>

    @Insert
    fun insert(favEntity: FavEntity)

    @Delete
    fun delete(favEntity: FavEntity)
}