package com.elmirov.shiftlabtesttask.data.repository

import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource,
) : SessionRepository {

    override fun getName(): Flow<String> =
        dataSource.getName()

    override fun isAuthorized(): Flow<Boolean> =
        dataSource.isAuthorized()
}