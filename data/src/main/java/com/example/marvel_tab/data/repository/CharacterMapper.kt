package com.example.marvel_tab.data.repository

import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.data.model.CharacterEntity
import com.example.marvel_tab.data.model.CharacterListResponse

object CharacterMapper {
    fun CharacterListResponse.toDomain(): List<Character> {
        return this.results.map {
            Character(
                id = it.id,
                name = it.name,
                description = it.description,
                thumbnail = "${it.thumbnail.path}.${it.thumbnail.extension}",
                isFavorite = false
            )
        }
    }

    fun List<CharacterEntity>.toDomain(): List<Character> {
        return this.map {
            Character(
                id = it.id,
                name = it.name ?: "",
                description = it.description ?: "",
                thumbnail = it.thumbnailUrl ?: "",
                isFavorite = true
            )
        }
    }
}