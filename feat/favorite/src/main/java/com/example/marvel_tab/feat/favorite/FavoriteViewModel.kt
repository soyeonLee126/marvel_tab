package com.example.marvel_tab.feat.favorite

import androidx.lifecycle.ViewModel
import com.example.marvel_tab.core.usecase.GetFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.usecase.DeleteFavoriteCharacterUseCase
import com.example.marvel_tab.core.util.ResourceProvider
import com.example.marvel_tab.core.ui.R.string

@HiltViewModel
class FavoriteViewModel @Inject constructor (
    private val resourceProvider: ResourceProvider,
    private val getFavoriteCharacterUseCase: GetFavoriteCharacterUseCase,
    private val deleteFavoriteCharacterUseCase: DeleteFavoriteCharacterUseCase
): ViewModel(), ContainerHost<FavoriteUiState, Unit> {
    override val container : Container<FavoriteUiState, Unit> = container(FavoriteUiState())

    init {
        loadFavorite()
    }

    private fun loadFavorite() = intent {
        getFavoriteCharacterUseCase().collectLatest {
            if (it.isEmpty()) {
                reduce {
                    state.copy(
                        errorMessage =  resourceProvider.getString(string.empty_favorite_list_message)
                    )
                }
            }
            reduce {
                state.copy(
                    favoriteCharacters = it.map { character ->
                        Character(
                            id = character.id,
                            name = character.name,
                            description = character.description,
                            thumbnail = character.thumbnail,
                            isFavorite = true
                        )
                    }
                )
            }
        }
    }

    fun onCardClick(character: Character) = intent {
        deleteFavoriteCharacterUseCase(character)
    }
}