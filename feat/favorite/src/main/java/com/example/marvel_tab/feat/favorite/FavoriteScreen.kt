package com.example.marvel_tab.feat.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marvel_tab.core.ui.component.CharacterCard
import org.orbitmvi.orbit.compose.collectAsState
import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.ui.component.InfinityLazyVerticalGrid

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState()

    FavoriteScreen(
        state = state.value,
        onCardClick = viewModel::onCardClick
    )
}

@Composable
fun FavoriteScreen(
    state: FavoriteUiState,
    onCardClick: (Character) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (state.errorMessage.isNotEmpty()) Text(
            state.errorMessage, modifier = Modifier.align(
                Alignment.Center
            )
        )
        InfinityLazyVerticalGrid(
            modifier = modifier.padding(top = 20.dp),
            content = {
                items(state.favoriteCharacters.size, key = { state.favoriteCharacters[it].id }) {
                    CharacterCard(
                        name = state.favoriteCharacters[it].name,
                        description = state.favoriteCharacters[it].description,
                        imageUrl = state.favoriteCharacters[it].thumbnail,
                        onCLick = { onCardClick(state.favoriteCharacters[it]) },
                        isFavorite = state.favoriteCharacters[it].isFavorite
                    )
                }
            }
        )
        if (state.isLoading) CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center)
        )
    }
}