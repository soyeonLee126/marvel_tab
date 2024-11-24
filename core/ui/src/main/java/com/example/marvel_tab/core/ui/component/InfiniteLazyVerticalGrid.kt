package com.example.marvel_tab.core.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InfinityLazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loadMoreLimitCount: Int = 10,
    loadMore: () -> Unit = {},
    content: LazyGridScope.() -> Unit,
) {
    state.onLoadMore(limitCount = loadMoreLimitCount, action = loadMore)

    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        content = content,
        columns = GridCells.Fixed(2)
    )
}

@SuppressLint("ComposableNaming")
@Composable
private fun LazyGridState.onLoadMore(limitCount: Int = 6, loadOnBottom: Boolean = true, action: () -> Unit) {
    val reached by remember {
        derivedStateOf {
            reachedBottom(limitCount = limitCount, triggerOnEnd = loadOnBottom)
        }
    }
    LaunchedEffect(reached) {
        if (reached) action()
    }
}

private fun LazyGridState.reachedBottom(
    limitCount: Int = 2,
    triggerOnEnd: Boolean = false,
): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return (triggerOnEnd && lastVisibleItem?.index == layoutInfo.totalItemsCount - 1)
            || lastVisibleItem?.index != 0 && lastVisibleItem?.index == layoutInfo.totalItemsCount - (limitCount + 1)
}