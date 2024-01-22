package com.elmirov.shiftlabtesttask.di.module

import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSourceImpl
import com.elmirov.shiftlabtesttask.data.repository.RegistrationRepositoryImpl
import com.elmirov.shiftlabtesttask.data.repository.SessionRepositoryImpl
import com.elmirov.shiftlabtesttask.di.annotation.ApplicationScope
import com.elmirov.shiftlabtesttask.domain.repository.RegistrationRepository
import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @ApplicationScope
    fun bindRegistrationRepository(impl: RegistrationRepositoryImpl): RegistrationRepository

    @Binds
    @ApplicationScope
    fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository
}