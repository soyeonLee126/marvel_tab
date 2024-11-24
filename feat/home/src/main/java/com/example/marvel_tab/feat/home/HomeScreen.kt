package com.example.marvel_tab.feat.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.ui.component.CharacterCard
import com.example.marvel_tab.core.ui.component.InfinityLazyVerticalGrid
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
        onSearch = viewModel::onSearch,
        onCardClick = viewModel::onCardClick
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onCardClick: (Character) -> Unit,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        if (state.isLoading) CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center)
        )
        MarvelSearchBar(
            query = state.searchQuery,
            onQueryChanged = onQueryChanged,
            onSearch = onSearch
        )
        InfinityLazyVerticalGrid(
            loadMore = onSearch,
            modifier = modifier.padding(top = 90.dp),
        ) {
            items(state.characters.size) {
                CharacterCard(
                    name = state.characters[it].name,
                    description = state.characters[it].description,
                    imageUrl = state.characters[it].thumbnail,
                    onCLick = { onCardClick(state.characters[it]) },
                    isFavorite = state.characters[it].isFavorite
                )
            }
        }
    }
}
