package com.example.marvel_tab.feat.home

import com.example.marvel_tab.core.model.Character

data class HomeUiState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val characters: List<Character> = emptyList(),
    val favoriteCharacters: List<Character> = emptyList(),
    val searchQuery: String = ""
)