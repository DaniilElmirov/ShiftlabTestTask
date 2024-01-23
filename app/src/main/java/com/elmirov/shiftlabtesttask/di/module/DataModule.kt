package com.elmirov.shiftlabtesttask.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSource
import com.elmirov.shiftlabtesttask.data.datasource.LocalDataSourceImpl
import com.elmirov.shiftlabtesttask.data.repository.RegistrationRepositoryImpl
import com.elmirov.shiftlabtesttask.data.repository.SessionRepositoryImpl
import com.elmirov.shiftlabtesttask.di.annotation.ApplicationScope
import com.elmirov.shiftlabtesttask.domain.repository.RegistrationRepository
import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [BindDataModule::class])
class DataModule {
    private companion object {
        private const val USER_DATA_STORE = "user_data_store"

        private val Context.createDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATA_STORE)
    }

    @Provides
    @ApplicationScope
    fun provideDataStore(context: Context): DataStore<Preferences> =
        context.createDataStore
}

@Module
interface BindDataModule {
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