package com.elmirov.shiftlabtesttask.data.datasource

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

interface LocalDataSource {
    fun register(name: String)

    fun getName(): String?
}

class LocalDataSourceImpl @Inject constructor(
    private val context: Context,
) : LocalDataSource {

    companion object {
        private const val PREFS_NAME = "name"
        private const val USER_NAME_KEY = "user name"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun register(name: String) {
        sharedPreferences
            .edit()
            .putString(USER_NAME_KEY, name)
            .apply()
    }

    override fun getName(): String? =
        sharedPreferences.getString(USER_NAME_KEY, null)
}

