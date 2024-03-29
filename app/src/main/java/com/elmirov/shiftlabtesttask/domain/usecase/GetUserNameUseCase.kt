package com.elmirov.shiftlabtesttask.domain.usecase

import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val repository: SessionRepository,
) {
    operator fun invoke(): Flow<String> =
        repository.getName()
}