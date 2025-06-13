package com.chomp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class LoginViewModel : ViewModel() {

    private var _state = MutableStateFlow<LoginState>(LoginState.Initial)
    val uiState get() = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            LoginState.Initial
        )

    fun processInput(one: String, two: String) = viewModelScope.launch {
        _state.update { LoginState.Initial }
        delay(3000L)
        // some processing in use case, in at least IO dispatcher
        _state.update { LoginState.Success }
    }

    fun resetState() = _state.update { LoginState.Initial }

    sealed class LoginState {
        data object Loading : LoginState()
        data object Initial : LoginState()
        data object Success: LoginState()
        data object Error: LoginState()
    }
}