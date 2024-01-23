package com.elmirov.shiftlabtesttask.data.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.elmirov.shiftlabtesttask.data.user.UserScheme
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
    }

    override suspend fun register(user: User) {
        dataStore.edit {
            it[UserScheme.NAME] = user.name
            it[UserScheme.SECOND_NAME] = user.secondName
            it[UserScheme.DATE_OF_BIRTH] = user.dateOfBirth
            it[UserScheme.PASSWORD] = user.password
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
            it[UserScheme.NAME]!! //Есть проверка на авторизацию
        }

    override fun isAuthorized(): Flow<Boolean> =
        dataStore.data.map {
            it[UserScheme.NAME] != null
        }
}

