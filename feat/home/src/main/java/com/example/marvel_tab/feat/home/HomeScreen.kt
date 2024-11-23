package com.example.marvel_tab.feat.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marvel_tab.feat.home.component.MarvelSearchBar
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCharacters()
    }

    HomeScreen(
        state = state.value,
        onQueryChanged = viewModel::onQueryChanged,
        onSearch = viewModel::onSearch
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onQueryChanged: (String) -> Unit,
    onSearch:() -> Unit
) {
    MarvelSearchBar(
        query = state.searchQuery,
        onQueryChanged = onQueryChanged,
        onSearch = onSearch
    )
}
