package com.chomp.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.chomp.library.data.FakeHost
import com.chomp.library.data.Faker
import com.chomp.library.ui.SCREEN_PADDING
import com.chomp.library.ui.ShowAlertDialog
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
internal fun DetailsScreen(
    navController: NavHostController = FakeHost(LocalContext.current),
    id: Int = -1 // for later
) {
    injectDetailsModule()
    val detailsVM = koinViewModel<DetailsViewModel>()

    val state by detailsVM.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SCREEN_PADDING)
    ) {
        Text(
            text ="Received id from previous screen $id",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        when (val uiState = state) {
            DetailsViewModel.DetailsState.FlowComplete -> ShowAlertDialog("Flow is complete") {  }
            is DetailsViewModel.DetailsState.ItemReceived -> Item(uiState.data)
            DetailsViewModel.DetailsState.Loading -> {}
        }
    }

}

@Composable
private fun Item(
    id: Faker
) {
    Text(
        text ="Received ${id.title}",
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}