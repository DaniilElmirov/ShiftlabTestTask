package com.elmirov.shiftlabtesttask.presentation.viewmodel

import com.elmirov.shiftlabtesttask.domain.usecase.GetAuthorizationUseCase
import com.elmirov.shiftlabtesttask.navigation.router.MainActivityRouter
import com.elmirov.shiftlabtesttask.presentation.activity.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainActivityViewModelTest {

    private val router: MainActivityRouter = mock()
    private val getAuthorizationUseCase: GetAuthorizationUseCase = mock()

    private val viewModel = MainActivityViewModel(router, getAuthorizationUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `user not registered EXPECT open registration`() = runTest {
        whenever(getAuthorizationUseCase()) doReturn flowOf(false)
        viewModel.navigate()

        verify(router).openRegistration()
    }

    @Test
    fun `user registered EXPECT open greeting`() = runTest {
        whenever(getAuthorizationUseCase()) doReturn flowOf(true)
        viewModel.navigate()

        verify(router).openGreeting()
    }
}