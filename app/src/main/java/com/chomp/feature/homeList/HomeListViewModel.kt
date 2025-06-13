package com.chomp.feature.homeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chomp.library.core.Failure
import com.chomp.library.core.Response
import com.chomp.library.data.Faker
import com.chomp.library.domain.FetchDataResponseUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeListViewModel(
    private val fetchDataResponse: FetchDataResponseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeState>(value = HomeState.Loading)
    val uiState = _uiState
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeState.Loading
        )

    init {
        fetchFakers()
    }

    fun fetchFakers() = viewModelScope.launch {

        _uiState.update { HomeState.Loading }

        delay(2000L)

        fetchDataResponse().collect { res ->
            _uiState.update {
                when(res) {
                    is Response.Failure<Failure> -> HomeState.Error("some error")
                    is Response.Success<List<Faker>> -> HomeState.DataList(res.success)
                }
            }

        }
    }

    fun fetchContFlow() = viewModelScope.launch {
        // collect continuous flow and then
        // update state with new value item
    }

    sealed class HomeState {
        data object Loading : HomeState()
        data class Error(val msg: String) : HomeState()
        data class DataList(val data: List<Faker>) : HomeState()
        data class NewItem(val data: Faker) : HomeState()
    }
}