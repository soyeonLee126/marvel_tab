package com.example.marvel_tab.core.usecase

import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(query: String) {

    }
}