package com.chomp.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chomp.library.data.Faker
import com.chomp.library.domain.FetchDataFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DetailsViewModel(
    private val fetchDataFlow: FetchDataFlowUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val uiState get() = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            DetailsState.Loading
        )

    init {
        fetchContFlow()
    }

    private fun fetchContFlow() = viewModelScope.launch {
        _state.update { DetailsState.Loading }
        fetchDataFlow()
            .onCompletion {
            _state.update { DetailsState.FlowComplete }
        }
            .collectLatest { item ->
            _state.update { DetailsState.ItemReceived(item) }
        }
    }

    sealed class DetailsState {
        data object Loading : DetailsState()
        data object FlowComplete : DetailsState()
        data class ItemReceived(val data: Faker) : DetailsState()
    }
}