package com.ashutosh.wallpaperapp.hilt

import android.content.Context
import com.ashutosh.wallpaperapp.network.ApiService
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
    fun kuchBhi(@ApplicationContext context: Context):ApiService{
        return ApiService.getInstance(context)
    }

}