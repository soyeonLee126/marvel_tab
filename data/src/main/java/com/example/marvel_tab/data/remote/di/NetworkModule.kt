package com.example.marvel_tab.data.remote.di

import com.example.marvel_tab.data.BuildConfig
import com.example.marvel_tab.data.remote.DevelopmentInterceptor
import com.example.marvel_tab.data.remote.adapter.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
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
        jsonConverterFactory: Converter.Factory,
        developmentInterceptor: DevelopmentInterceptor
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(
            okhttpClientBuilder
                .addInterceptor(developmentInterceptor)
                .build()
        )
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .addConverterFactory(jsonConverterFactory)
        .build()


    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = false
            prettyPrint = true
        }
    }

    @Provides
    @Singleton
    @Suppress("JSON_FORMAT_REDUNDANT")
    fun provideJsonConverterFactory(): Converter.Factory =
        Json { ignoreUnknownKeys = true }.asConverterFactory("application/json; charset=UTF8".toMediaType())
}