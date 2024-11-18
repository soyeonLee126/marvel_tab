package com.example.marvel_tab.feat.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState()
    HomeScreen(
        state = state.value
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState
) {
    Text(text = "Home")
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