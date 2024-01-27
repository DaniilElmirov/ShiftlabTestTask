package com.elmirov.shiftlabtesttask.screen

import com.elmirov.shiftlabtesttask.R
import com.elmirov.shiftlabtesttask.ui.RegistrationFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.picker.date.KDatePicker
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.toolbar.KToolbar

object RegistrationScreen : KScreen<RegistrationScreen>() {
    override val layoutId: Int? = R.layout.fragment_registration
    override val viewClass: Class<*>? = RegistrationFragment::class.java

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val name = KTextInputLayout { withId(R.id.name) }

    val secondName = KTextInputLayout { withId(R.id.second_name) }

    val dateOfBirth = KTextInputLayout { withId(R.id.date_of_birth) }

    val datePicker = KDatePicker { withText(R.string.enter_date_of_birth) }

    val password = KTextInputLayout { withId(R.id.password) }

    val repeatedPassword = KTextInputLayout { withId(R.id.repeated_password) }

    val registration = KButton { withId(R.id.registration) }
}