package com.elmirov.shiftlabtesttask.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getName(): Flow<String>

    fun isAuthorized(): Flow<Boolean>
}