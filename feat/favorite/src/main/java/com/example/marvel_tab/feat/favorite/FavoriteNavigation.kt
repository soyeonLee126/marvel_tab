package com.example.marvel_tab.feat.favorite

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val FAVORITE_ROUTE = "favorite"

fun NavGraphBuilder.navigateFavorite() {
    composable(FAVORITE_ROUTE) { FavoriteScreen() }
}