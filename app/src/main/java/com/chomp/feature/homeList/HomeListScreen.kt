package com.chomp.feature.homeList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.chomp.feature.homeList.list.ItemCard
import com.chomp.library.data.FAKE_DATA
import com.chomp.library.data.FakeHost
import com.chomp.library.data.Faker
import com.chomp.library.ui.GRID_CELL
import com.chomp.library.ui.LIST_ITEM_SPACING
import com.chomp.library.ui.SCREEN_PADDING
import com.chomp.library.ui.ShowAlertDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@Composable
internal fun HomeListScreen(
    navController: NavHostController = FakeHost(LocalContext.current),
) {

    // will koin injecting cause unpredicted side effects?
    // does hilt not have this issue?
    injectHomeModule()

    val homeViewModel: HomeListViewModel = koinViewModel()

   val state by homeViewModel.uiState.collectAsStateWithLifecycle()

    when (val screenState = state) {
        is HomeListViewModel.HomeState.DataList -> HomeListDataScreen(screenState.data)
        is HomeListViewModel.HomeState.Error -> ShowAlertDialog("Something went wrong...") {  }
        HomeListViewModel.HomeState.Loading -> { }
        is HomeListViewModel.HomeState.NewItem -> { }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun HomeListDataScreen(
    items: List<Faker> = FAKE_DATA
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SCREEN_PADDING)
    ) {
        val listState = rememberLazyStaggeredGridState()
        LazyVerticalStaggeredGrid(
            state = listState,
            columns = StaggeredGridCells.Adaptive(GRID_CELL),
            verticalItemSpacing = LIST_ITEM_SPACING,
            horizontalArrangement = Arrangement.spacedBy(LIST_ITEM_SPACING),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 32.dp,
                    start = SCREEN_PADDING,
                    end = SCREEN_PADDING,
                    bottom = SCREEN_PADDING
                )
        ) {
            items(
                items = items,
                key = { it.id }
            ) { faker ->
                ItemCard(faker)
            }
        }
    }
}