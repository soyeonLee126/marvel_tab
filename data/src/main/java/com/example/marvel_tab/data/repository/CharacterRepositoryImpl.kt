package com.example.marvel_tab.data.repository

import com.example.marvel_tab.core.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl() : CharacterRepository {
    override suspend fun getCharacters(query: String): Flow<List<Character>> = flow {
        emit(emptyList())
    }
    override suspend fun saveCharacter(character: Character) {

    }
    override suspend fun deleteCharacter(character: Character) {

    }
    override suspend fun getFavoriteCharacters(): Flow<List<Character>> = flow {
        emit(emptyList())
    }
}