package com.example.marvel_tab.feat.home

import androidx.lifecycle.ViewModel
import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.usecase.GetCharactersUseCase
import com.example.marvel_tab.core.usecase.GetFavoriteCharacterUseCase
import com.example.marvel_tab.core.usecase.SaveFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getCharactersUseCase: GetCharactersUseCase,
    val saveFavoriteCharacterUseCase: SaveFavoriteCharacterUseCase,
    val getFavoriteCharacterUseCase: GetFavoriteCharacterUseCase,
) : ViewModel(), ContainerHost<HomeUiState, Unit> {
    override val container: Container<HomeUiState, Unit> = container(HomeUiState())

    init {
        getFavoriteCharacters()
    }

    private fun getFavoriteCharacters() = intent {
        getFavoriteCharacterUseCase().collectLatest {
            reduce {
                state.copy(
                    favoriteCharacters = it
                )
            }
        }
    }

    fun getCharacters() = intent {
        reduce {
            state.copy(
                isLoading = true,
                isError = false
            )
        }
        getCharactersUseCase(state.searchQuery).collectLatest { characters ->
            reduce {
                state.copy(
                    isLoading = false,
                    isError = false,
                    characters = characters.map { character ->
                        character.copy(
                            isFavorite = state.favoriteCharacters.any { it.id == character.id }
                        )
                    }
                )
            }
        }
    }

    fun onQueryChanged(query: String) = blockingIntent {
        reduce {
            state.copy(
                searchQuery = query
            )
        }
    }

    fun onSearch() = intent {
        getCharacters()
    }

    fun onCardClick(character: Character) = intent {
        saveFavoriteCharacterUseCase(character)
    }
}