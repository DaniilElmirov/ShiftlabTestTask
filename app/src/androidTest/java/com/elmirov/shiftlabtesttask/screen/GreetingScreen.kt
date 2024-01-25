package com.elmirov.shiftlabtesttask.screen

import com.elmirov.shiftlabtesttask.R
import com.elmirov.shiftlabtesttask.ui.GreetingFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.dialog.KAlertDialog
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.toolbar.KToolbar

object GreetingScreen : KScreen<GreetingScreen>() {
    override val layoutId: Int? = R.layout.fragment_greeting
    override val viewClass: Class<*>? = GreetingFragment::class.java


    val toolbar = KToolbar { withId(R.id.toolbar) }

    val greeting = KButton { withId(R.id.greeting) }

    val dialog = KAlertDialog()
}