package com.example.country.di

import android.content.Context
import android.content.SharedPreferences
import com.example.country.util.Constants
import com.example.country.util.FavoriteManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideFavoriteManager(preferences: SharedPreferences) = FavoriteManager(preferences)
}