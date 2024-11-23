package com.example.marvel_tab.data.repository

import com.example.marvel_tab.core.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(name: String): Flow<List<Character>>
    suspend fun saveCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
    suspend fun getFavoriteCharacters(): Flow<List<Character>>
}