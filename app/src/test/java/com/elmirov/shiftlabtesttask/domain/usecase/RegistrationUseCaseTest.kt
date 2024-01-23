package com.elmirov.shiftlabtesttask.domain.usecase

import com.elmirov.shiftlabtesttask.domain.repository.RegistrationRepository
import com.elmirov.shiftlabtesttask.util.ProjectData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RegistrationUseCaseTest {

    private val registrationRepository: RegistrationRepository = mock()
    private val useCase = RegistrationUseCase(registrationRepository)

    @Test
    fun `user registered EXPECT registration`() = runTest {
        useCase(ProjectData.user)

        verify(registrationRepository).register(ProjectData.user)
    }
}