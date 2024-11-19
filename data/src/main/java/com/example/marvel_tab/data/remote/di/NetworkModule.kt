package com.example.marvel_tab.data.remote.di

import com.example.marvel_tab.data.remote.DevelopmentInterceptor
import com.example.marvel_tab.data.remote.adapter.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn
@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
    }

    @Provides
    fun provideOkHttpClient(
        okhttpClientBuilder: OkHttpClient.Builder,
    ): OkHttpClient {
        return okhttpClientBuilder
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Marvel

    @Provides
    @Singleton
    @Marvel
    fun provideRetrofit(
        okhttpClientBuilder: OkHttpClient.Builder,
        developmentInterceptor: DevelopmentInterceptor,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                okhttpClientBuilder
                    .build()
            )
            .addInterceptor(developmentInterceptor)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
            prettyPrint = true
        }
    }
}