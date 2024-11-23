package com.example.marvel_tab.feat.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marvel_tab.core.ui.component.CharacterCard
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCharacters("test")
    }

    HomeScreen(
        state = state.value
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState
) {
    CharacterCard(
        "test",
        "description"
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeUiState(
            isLoading = false,
            isError = false,
        )
    )
}