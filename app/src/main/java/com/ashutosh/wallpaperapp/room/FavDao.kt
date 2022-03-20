package com.ashutosh.wallpaperapp.room

import androidx.room.*

@Dao
interface FavDao {
    @Query("SELECT * From favTable where wallId = :id  ")
    suspend fun getFav(id:Int):FavEntity?

    @Query("SELECT * From favTable")
    suspend fun getAllFav(): List<FavEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favEntity: FavEntity)

    @Delete
    suspend fun delete(favEntity: FavEntity)
}