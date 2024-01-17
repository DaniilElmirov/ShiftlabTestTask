package com.elmirov.shiftlabtesttask.domain.usecase

import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val repository: SessionRepository,
) {
    operator fun invoke(): String? =
        repository.getName()
}