package com.example.marvel_tab.data.repository

interface CharacterRepository {
    suspend fun getCharacters(id: Int): Character
    suspend fun saveCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
    suspend fun getFavoriteCharacters(): List<Character>
}