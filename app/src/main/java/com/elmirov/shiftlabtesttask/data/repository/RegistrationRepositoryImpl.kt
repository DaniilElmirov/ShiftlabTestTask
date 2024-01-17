package com.elmirov.shiftlabtesttask.data.repository

import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.domain.entity.User
import com.elmirov.shiftlabtesttask.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource,
) : RegistrationRepository {
    override fun register(user: User) {
        dataSource.register(name = user.name)
    }
}