package com.example.marvel_tab.data.repository

import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.repository.CharacterRepository
import com.example.marvel_tab.data.local.CharacterDao
import com.example.marvel_tab.data.remote.api.CharacterService
import com.example.marvel_tab.data.repository.CharacterMapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : CharacterRepository {
    override suspend fun getCharacters(name: String): Flow<List<Character>> = flow {
        emit(characterService.getCharacters(name = name).getOrThrow().toDomain())
    }
    override suspend fun saveCharacter(character: Character) = characterDao.saveCharacter(character)
    override suspend fun deleteCharacter(character: Character) = characterDao.deleteCharacter(character.id)
    override suspend fun getFavoriteCharacters(): Flow<List<Character>> = flow {
        emit(characterDao.getFavoriteCharacters().)
    }
}