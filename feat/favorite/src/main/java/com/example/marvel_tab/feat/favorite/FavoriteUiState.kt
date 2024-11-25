package com.example.marvel_tab.feat.favorite

import com.example.marvel_tab.core.model.Character

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val favoriteCharacters: List<Character> = emptyList(),
)
