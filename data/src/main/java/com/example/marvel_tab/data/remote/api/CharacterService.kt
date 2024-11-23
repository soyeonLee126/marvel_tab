package com.example.marvel_tab.data.remote.api

import com.example.marvel_tab.data.BuildConfig
import com.example.marvel_tab.data.model.CharacterListResponse
import com.example.marvel_tab.data.remote.adapter.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("limit") limit: Int = PAGE_LIMIT,
        @Query("name") name: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String
        ) : ApiResult<CharacterListResponse>
}

const val PAGE_LIMIT = 10