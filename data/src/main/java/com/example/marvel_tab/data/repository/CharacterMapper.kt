package com.example.marvel_tab.data.repository

import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.data.model.CharacterEntity
import com.example.marvel_tab.data.model.CharacterListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    fun Flow<List<CharacterEntity>>.toDomain(): Flow<List<Character>> {
        return this.map { list ->
            list.map {
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

    fun Character.toEntity(): CharacterEntity = CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnail
    )
}