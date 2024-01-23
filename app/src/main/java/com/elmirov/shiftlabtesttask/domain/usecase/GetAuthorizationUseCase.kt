package com.elmirov.shiftlabtesttask.domain.usecase

import com.elmirov.shiftlabtesttask.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthorizationUseCase @Inject constructor(
    private val repository: SessionRepository,
) {

    operator fun invoke(): Flow<Boolean> =
        repository.isAuthorized()
}