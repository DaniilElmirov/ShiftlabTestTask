package com.elmirov.shiftlabtesttask.util

import com.elmirov.shiftlabtesttask.domain.entity.User
import kotlinx.coroutines.flow.flowOf

object ProjectData {
    val user = User(
        name = "Name",
        secondName = "SecondName",
        dateOfBirth = "01.01.1999",
        password = "1qW",
    )

    val userRegistered = flowOf(false, true)
    val userLogout = flowOf(true, false)
}