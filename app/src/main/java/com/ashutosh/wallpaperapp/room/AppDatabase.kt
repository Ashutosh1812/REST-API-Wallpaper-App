package com.ashutosh.wallpaperapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavEntity::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao():FavDao
    companion object{
        fun instance(context: Context): AppDatabase {
            return Room.databaseBuilder(context,AppDatabase::class.java,"db").build()
        }
    }
}