package com.ashutosh.wallpaperapp.hilt

import android.content.Context
import com.ashutosh.wallpaperapp.network.ApiService
import com.ashutosh.wallpaperapp.room.AppDatabase
import com.ashutosh.wallpaperapp.room.FavDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun apiService(@ApplicationContext context: Context): ApiService {
        return ApiService.getInstance(context)
    }
    @Provides
    @Singleton
    fun dataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.instance(context)
    }
    
    @Provides
    @Singleton
    fun dao(appDatabase: AppDatabase): FavDao {
        return appDatabase.favDao()
    }

}