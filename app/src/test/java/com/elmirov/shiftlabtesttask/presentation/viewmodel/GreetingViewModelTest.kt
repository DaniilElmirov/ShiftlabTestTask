package com.elmirov.shiftlabtesttask.presentation.viewmodel

import app.cash.turbine.test
import com.elmirov.shiftlabtesttask.domain.usecase.GetUserNameUseCase
import com.elmirov.shiftlabtesttask.presentation.greeting.state.GreetingState
import com.elmirov.shiftlabtesttask.presentation.greeting.viewmodel.GreetingViewModel
import com.elmirov.shiftlabtesttask.util.ProjectData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GreetingViewModelTest {

    private val getUserNameUseCase: GetUserNameUseCase = mock()

    private val viewModel = GreetingViewModel(getUserNameUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when started EXPECT initial state`() = runTest {
        viewModel.state.test {
            assertEquals(GreetingState.Initial, awaitItem())
        }
    }

    @Test
    fun `greeting EXPECT state content`() = runTest {
        whenever(getUserNameUseCase()) doReturn flowOf(ProjectData.user.name)

        viewModel.greeting()
        viewModel.state.test {
            assertEquals(GreetingState.Content(ProjectData.user.name), awaitItem())
        }
    }

    @Test
    fun `close greeting EXPECT initial state`() = runTest {
        viewModel.closeGreeting()

        viewModel.state.test {
            assertEquals(GreetingState.Initial, awaitItem())
        }
    }
}