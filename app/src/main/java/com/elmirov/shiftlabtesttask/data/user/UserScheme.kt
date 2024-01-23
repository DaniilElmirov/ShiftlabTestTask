package com.elmirov.shiftlabtesttask.data.user

import androidx.datastore.preferences.core.stringPreferencesKey

object UserScheme {
    val NAME = stringPreferencesKey("name")
    val SECOND_NAME = stringPreferencesKey("second_name")
    val DATE_OF_BIRTH = stringPreferencesKey("date_of_birth")
    val PASSWORD = stringPreferencesKey("password")
}