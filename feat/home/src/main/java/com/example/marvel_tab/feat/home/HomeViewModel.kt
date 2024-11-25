package com.example.marvel_tab.feat.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.usecase.GetCharactersUseCase
import com.example.marvel_tab.core.usecase.GetFavoriteCharacterUseCase
import com.example.marvel_tab.core.usecase.GetMoreCharactersUseCase
import com.example.marvel_tab.core.usecase.SaveFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getMoreCharactersUseCase: GetMoreCharactersUseCase,
    private val saveFavoriteCharacterUseCase: SaveFavoriteCharacterUseCase,
    private val getFavoriteCharacterUseCase: GetFavoriteCharacterUseCase,
) : ViewModel(), ContainerHost<HomeUiState, Unit> {

    override val container: Container<HomeUiState, Unit> = container(HomeUiState())
    private var currentJob: Job? = null

    init {
        loadFavoriteCharacters()
    }

    private fun loadFavoriteCharacters() = intent {
        getFavoriteCharacterUseCase().collectLatest { favoriteCharacters ->
            setLoadingState(false)
            reduce {
                val characters = mapCharactersWithFavorites(
                    state.characters,
                    favoriteCharacters = favoriteCharacters
                )
                state.copy(favoriteCharacters = favoriteCharacters, characters = characters)
            }
        }
    }

    fun getCharacters() = intent {
        setLoadingState(true)
        getCharactersUseCase(state.searchQuery).collectLatest { characters ->
            setLoadingState(false)
            val updatedCharacters = mapCharactersWithFavorites(
                characters,
                favoriteCharacters = state.favoriteCharacters
            )
            reduce {
                state.copy(characters = updatedCharacters)
            }
        }
    }

    fun loadMoreCharacters() = intent {
        setLoadingState(true)
        val oldCharacters = state.characters
        getMoreCharactersUseCase().collectLatest { characters ->
            setLoadingState(false)
            val updatedCharacters = mapCharactersWithFavorites(
                characters,
                favoriteCharacters = state.favoriteCharacters
            )
            reduce {
                state.copy(characters = oldCharacters + updatedCharacters)
            }
        }
    }

    fun onQueryChanged(query: String) = blockingIntent {
        updateSearchQuery(query)
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            delay(DELAY_TIME)
            if (query.length >= LIMIT_QUERY) {
                getCharacters()
            }
        }
    }

    fun onSearch() = intent {
        getCharacters()
    }

    fun onCardClick(character: Character) = intent {
        saveFavoriteCharacterUseCase(character)
    }

    private fun setLoadingState(isLoading: Boolean) = intent {
        reduce {
            state.copy(
                isLoading = isLoading,
                isError = false
            )
        }
    }

    private fun mapCharactersWithFavorites(
        characters: List<Character>,
        favoriteCharacters: List<Character>
    ): List<Character> {
        return characters.map { character ->
            character.copy(
                isFavorite = favoriteCharacters.any { it.id == character.id }
            )
        }
    }

    private fun updateSearchQuery(query: String) = intent {
        reduce {
            state.copy(searchQuery = query)
        }
    }

    companion object {
        private const val LIMIT_QUERY = 2
        private const val DELAY_TIME = 300L
    }
}