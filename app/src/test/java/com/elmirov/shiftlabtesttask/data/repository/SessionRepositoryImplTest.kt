package com.elmirov.shiftlabtesttask.data.repository

import app.cash.turbine.test
import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.util.ProjectData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SessionRepositoryImplTest {

    private val dataSource: LocalDataSource = mock()
    private val repository = SessionRepositoryImpl(dataSource)

    private val user = ProjectData.user

    @Test
    fun `get name EXPECT name`() = runTest {
        whenever(dataSource.getName()) doReturn flowOf(user.name)

        repository.getName().test {
            assertEquals(user.name, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when user registered EXPECT change authorized status`() = runTest {
        whenever(dataSource.isAuthorized()) doReturn ProjectData.userRegistered

        repository.isAuthorized().test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when user logout EXPECT change authorized status`() = runTest {
        whenever(dataSource.isAuthorized()) doReturn ProjectData.userLogout

        repository.isAuthorized().test {
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
            awaitComplete()
        }
    }
}