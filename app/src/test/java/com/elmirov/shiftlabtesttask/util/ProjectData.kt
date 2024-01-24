package com.elmirov.shiftlabtesttask.util

import com.elmirov.shiftlabtesttask.domain.entity.User
import kotlinx.coroutines.flow.flowOf

object ProjectData {

    const val rightName = "Name"
    const val wrongName = "N"

    const val rightSecondName = "SecondName"
    const val wrongSecondName = "S"

    const val rightDateOfBirth = "01.01.1999"
    const val wrongDateOfBirthFormat = "01011999"
    const val wrongDateOfBirthFuture = "31.12.9999"

    const val rightPassword = "1qW"
    const val wrongPassword = "a 1"

    const val rightRepeatedPassword = rightPassword
    const val wrongRepeatedPassword = "WrongPassword"

    val user = User(
        name = rightName,
        secondName = rightSecondName,
        dateOfBirth = rightDateOfBirth,
        password = rightPassword,
    )

    val userRegistered = flowOf(false, true)
    val userLogout = flowOf(true, false)
}