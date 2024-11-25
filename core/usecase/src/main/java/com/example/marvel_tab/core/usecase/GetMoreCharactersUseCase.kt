package com.example.marvel_tab.core.usecase

import com.example.marvel_tab.data.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.marvel_tab.core.model.Character

class GetMoreCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
     operator fun invoke(): Flow<List<Character>> =
        repository.getMoreCharacters()
}