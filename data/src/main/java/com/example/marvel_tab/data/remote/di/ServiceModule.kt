package com.example.marvel_tab.data.remote.di

import com.example.marvel_tab.data.remote.api.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    internal fun provideCharacterService(
        @NetworkModule.Marvel retrofit: Retrofit
    ): CharacterService = retrofit.create(CharacterService::class.java)
}