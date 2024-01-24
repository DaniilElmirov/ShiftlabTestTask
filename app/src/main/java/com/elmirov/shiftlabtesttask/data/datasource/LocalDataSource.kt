package com.elmirov.shiftlabtesttask.data.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.elmirov.shiftlabtesttask.domain.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface LocalDataSource {
    suspend fun register(user: User)

    fun getName(): Flow<String>

    fun isAuthorized(): Flow<Boolean>
}

class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalDataSource {

    private companion object {
        private const val LOG_TAG = "LocalDataSourceImpl"
        private const val STUB = "STUB)"

        val NAME = stringPreferencesKey("name")
        val SECOND_NAME = stringPreferencesKey("second_name")
        val DATE_OF_BIRTH = stringPreferencesKey("date_of_birth")
        val PASSWORD = stringPreferencesKey("password")
    }

    override suspend fun register(user: User) {
        dataStore.edit {
            it[NAME] = user.name
            it[SECOND_NAME] = user.secondName
            it[DATE_OF_BIRTH] = user.dateOfBirth
            it[PASSWORD] = user.password
        }
    }

    override fun getName(): Flow<String> =
        dataStore.data.catch {
            if (it is IOException) {
                Log.e(LOG_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[NAME] ?: STUB
        }

    override fun isAuthorized(): Flow<Boolean> =
        dataStore.data.map {
            it[NAME] != null
        }
}

