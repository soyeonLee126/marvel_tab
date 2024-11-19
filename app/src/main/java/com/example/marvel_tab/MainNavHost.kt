package com.example.marvel_tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.marvel_tab.feat.favorite.navigateFavorite
import com.example.marvel_tab.feat.home.HOME_ROUTE
import com.example.marvel_tab.feat.home.navigateToHome

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        modifier = modifier
    ) {
        navigateToHome()
        navigateFavorite()
    }
}