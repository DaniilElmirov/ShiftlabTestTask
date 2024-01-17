package com.elmirov.shiftlabtesttask.data.repository

import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource,
) : SessionRepository {
    override fun getName(): String? =
        dataSource.getName()
}