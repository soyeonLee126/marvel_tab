package com.example.marvel_tab.data.di

import com.example.marvel_tab.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterModule {

    @Binds
    @Singleton
    abstract fun bindsCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository
}