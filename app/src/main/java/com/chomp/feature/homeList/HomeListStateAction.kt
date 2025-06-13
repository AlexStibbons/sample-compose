package com.chomp.feature.homeList


internal sealed class HomeState {
    object Loading : HomeState()
    object Success : HomeState()
    sealed class Failure : HomeState() {
        object NoNetworkFailure : Failure()
        object ServerFailure : Failure()
    }
}

internal sealed class HomeAction {
    data class OpenItemDetails(val id: Int) : HomeAction()
}