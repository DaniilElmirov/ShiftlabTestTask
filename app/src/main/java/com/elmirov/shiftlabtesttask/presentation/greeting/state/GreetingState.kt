package com.elmirov.shiftlabtesttask.presentation.greeting.state

sealed interface GreetingState {

    data object Initial : GreetingState

    data class Content(
        val name: String,
    ) : GreetingState
}