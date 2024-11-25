package com.example.marvel_tab.core.usecase

import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.data.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): Flow<List<Character>> = repository.getFavoriteCharacters()
}