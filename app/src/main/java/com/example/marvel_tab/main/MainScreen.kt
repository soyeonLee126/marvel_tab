package com.example.marvel_tab.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.marvel_tab.MainNavHost
import com.example.marvel_tab.feat.favorite.FAVORITE_ROUTE
import com.example.marvel_tab.feat.home.HOME_ROUTE
import com.example.marvel_tab.ui.theme.Marvel_TabTheme
import com.example.marvel_tab.core.ui.R.string

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var state by remember { mutableIntStateOf(0) }
    val titles =
        listOf(stringResource(string.home_page_title), stringResource(string.favorite_page_title))

    Marvel_TabTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Box {
                    PrimaryTabRow(
                        modifier = modifier.padding(innerPadding),
                        selectedTabIndex = state
                    ) {
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = state == index,
                                onClick = { state = index },
                                text = {
                                    Text(
                                        text = title,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            )
                        }
                    }
                    MainNavHost(
                        navController = navController,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                    when (state) {
                        0 -> navController.navigate(HOME_ROUTE)
                        1 -> navController.navigate(FAVORITE_ROUTE)
                    }
                }
            }
        )
    }
}