package com.elmirov.shiftlabtesttask.domain.usecase

import app.cash.turbine.test
import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import com.elmirov.shiftlabtesttask.util.ProjectData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAuthorizationUseCaseTest {

    private val sessionRepository: SessionRepository = mock()
    private val useCase = GetAuthorizationUseCase(sessionRepository)

    @Test
    fun `when user not registered EXPECT no authorized`() = runTest {
        whenever(sessionRepository.isAuthorized()) doReturn flowOf(false)

        useCase().test {
            assertEquals(false, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `user pass registration EXPECT change to authorized`() = runTest {
        whenever(sessionRepository.isAuthorized()) doReturn ProjectData.userRegistered

        useCase().test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `user logout EXPECT change to no authorized`() = runTest {
        whenever(sessionRepository.isAuthorized()) doReturn ProjectData.userLogout

        useCase().test {
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
            awaitComplete()
        }
    }
}