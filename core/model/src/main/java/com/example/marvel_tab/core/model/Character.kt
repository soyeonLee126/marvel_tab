package com.example.marvel_tab.core.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean
)