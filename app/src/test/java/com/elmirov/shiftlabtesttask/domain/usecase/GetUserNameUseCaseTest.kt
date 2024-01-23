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

class GetUserNameUseCaseTest {

    private val sessionRepository: SessionRepository = mock()
    private val useCase = GetUserNameUseCase(sessionRepository)

    @Test
    fun `get user name EXPECT name`() = runTest {
        whenever(sessionRepository.getName()) doReturn flowOf(ProjectData.user.name)

        useCase().test {
            assertEquals(ProjectData.user.name, awaitItem())
            awaitComplete()
        }
    }
}