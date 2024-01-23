package com.elmirov.shiftlabtesttask.domain.usecase

import com.elmirov.shiftlabtesttask.domain.entity.User
import com.elmirov.shiftlabtesttask.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: RegistrationRepository,
) {
    suspend operator fun invoke(user: User) =
        repository.register(user)
}