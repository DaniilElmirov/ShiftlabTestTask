package com.elmirov.shiftlabtesttask.presentation.greeting.viewmodel

import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.domain.usecase.GetUserNameUseCase
import com.elmirov.shiftlabtesttask.presentation.greeting.state.GreetingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GreetingViewModel @Inject constructor(
    private val getUserNameUseCase: GetUserNameUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<GreetingState>(GreetingState.Initial)
    val state = _state.asStateFlow()

    fun greeting() {
        val name = getUserNameUseCase() ?: "NO REGISTERED" //TODO переделать
        _state.value = GreetingState.Content(name)
    }

    fun closeGreeting() {
        _state.value = GreetingState.Initial
    }
}