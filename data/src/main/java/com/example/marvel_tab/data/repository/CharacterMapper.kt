package com.example.marvel_tab.data.repository

import com.example.marvel_tab.data.model.CharacterResponse
import com.example.marvel_tab.core.model.Character

object CharacterMapper {
    fun List<CharacterResponse>.toDomain(): List<Character> {
        return this.map Character(
            id = id,
            name = name,
            description = description,
            thumbnail = "${thumbnail.path}.${thumbnail.extension}",
            isFavorite = false
        )
    }
}