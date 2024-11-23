package com.example.marvel_tab.core.usecase

import com.example.marvel_tab.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(query: String) = repository.getCharacters(query)
}