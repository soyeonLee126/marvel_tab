package com.example.marvel_tab.feat.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val  HOME_ROUTE = "home"
fun NavGraphBuilder.navigateToHome() {
     composable(HOME_ROUTE) { HomeScreen() }
}