package com.chomp.feature.login

/**
 * Improvement:
 * common states and errors used across multiple modules
 * could be a project level sealed interface classes
 * */
internal sealed class LoginState {
    object Loading : LoginState()
    object Success : LoginState()
    sealed class Failure : LoginState() {
        object NoNetworkFailure : Failure()
        object ServerFailure : Failure()
    }
}

internal sealed class LoginAction {
    object StarClick : LoginAction()
}