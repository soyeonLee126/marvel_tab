package com.example.marvel_tab.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterListResponse(
    val offset: Int = 0,
    val limit: Int = 0,
    val total: Int = 0,
    val count: Int = 0,
    val results: List<CharacterResponse> = emptyList()
)

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: Comics,
)

@Serializable
data class Thumbnail(
    val path: String,
    val extension: String
)

@Serializable
data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Comic>
)

@Serializable
data class Comic(
    val resourceURI: String,
    val name: String
)
