package com.example.marvel_tab.feat.home

import androidx.lifecycle.ViewModel
import com.example.marvel_tab.core.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getCharactersUseCase: GetCharactersUseCase
) : ViewModel(), ContainerHost<HomeUiState, Unit> {
    override val container: Container<HomeUiState, Unit> = container(HomeUiState())

    fun getCharacters(query: String) = intent {
        reduce {
            state.copy(
                isLoading = true,
                isError = false
            )
        }
        getCharactersUseCase(query).collectLatest {
            characters ->
            reduce {
                state.copy(
                    isLoading = false,
                    isError = false,
                    characters = characters
                )
            }
        }
    }
}