package com.elmirov.shiftlabtesttask.data.repository

import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.util.ProjectData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RegistrationRepositoryImplTest {

    private val dataSource: LocalDataSource = mock()
    private val repository = RegistrationRepositoryImpl(dataSource)

    private val user = ProjectData.user

    @Test
    fun `register user EXPECT registration`() = runTest {
        repository.register(user)

        verify(dataSource).register(user)
    }
}