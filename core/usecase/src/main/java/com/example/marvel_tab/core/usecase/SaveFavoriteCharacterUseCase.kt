package com.example.marvel_tab.core.usecase

import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.data.repository.CharacterRepository
import javax.inject.Inject

class SaveFavoriteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: Character) = repository.saveCharacter(character = character)
}