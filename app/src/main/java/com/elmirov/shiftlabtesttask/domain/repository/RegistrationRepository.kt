package com.elmirov.shiftlabtesttask.domain.repository

import com.elmirov.shiftlabtesttask.domain.entity.User

interface RegistrationRepository {
    suspend fun register(user: User)
}