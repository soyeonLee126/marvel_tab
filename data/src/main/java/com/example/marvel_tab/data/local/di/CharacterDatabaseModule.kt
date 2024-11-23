package com.example.marvel_tab.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.marvel_tab.data.local.CharacterDao
import com.example.marvel_tab.data.local.MarvelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MarvelDatabase {
        return Room.databaseBuilder(
            appContext,
            MarvelDatabase::class.java,
            "marvel.db"
        )
            .build()
    }
    @Provides
    @Singleton
    fun provideCharacterDao(database: MarvelDatabase): CharacterDao {
        return database.characterDao()
    }
}

