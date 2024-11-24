package com.example.marvel_tab.core.usecase

import com.example.marvel_tab.data.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.marvel_tab.core.model.Character

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
     operator fun invoke(query: String, offset: Int?): Flow<List<Character>> =
        repository.getCharacters(query, offset)
}