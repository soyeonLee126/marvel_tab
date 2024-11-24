package com.example.marvel_tab.main

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel: ViewModel(), ContainerHost<MainUiState, Unit> {
    override val container : Container<MainUiState, Unit> = container(MainUiState())
}