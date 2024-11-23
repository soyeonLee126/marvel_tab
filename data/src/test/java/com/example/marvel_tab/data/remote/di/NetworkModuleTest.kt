package com.example.marvel_tab.data.remote.di

import com.example.marvel_tab.data.BuildConfig
import com.example.marvel_tab.data.remote.DevelopmentInterceptor
import io.mockk.every
import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import io.mockk.mockk
import org.junit.Test

class NetworkModuleTest {
    private val networkModule = NetworkModule()

    @Test
    fun provideOkHttpClient는_okHttp_client_인스턴스를_반환한다() {
        // Given
        val builder = networkModule.provideOkHttpClientBuilder()

        // When
        val client = networkModule.provideOkHttpClient(builder)
        assertEquals(0, client.connectionSpecs.size)
    }

    @Test
    fun provideJson은_Json_인스턴스를_반환한다() {
        val json = networkModule.provideJson()
        assertEquals(true, json.configuration.isLenient)
        assertEquals(true, json.configuration.ignoreUnknownKeys)
        assertEquals(true, json.configuration.encodeDefaults)
        assertEquals(true, json.configuration.prettyPrint)
    }

    @Test
    fun provideRetrofit은_Retrofit_인스턴스를_반환한다() {
        val builder = networkModule.provideOkHttpClientBuilder()
        val mockInterceptor = mockk<DevelopmentInterceptor>(relaxed = true)
        val json = networkModule.provideJson()

        val baseUrl = "https://gateway.marvel.com"
        every { BuildConfig.BASE_URL } returns baseUrl

        val retrofit = networkModule.provideRetrofit(builder,  mockInterceptor, json)

        assertEquals(baseUrl, retrofit.baseUrl().toString())
        assertEquals(OkHttpClient::class.java, retrofit.callFactory().javaClass)
        assert(retrofit.converterFactories().any { it is kotlinx.serialization.json.Json })
    }
}