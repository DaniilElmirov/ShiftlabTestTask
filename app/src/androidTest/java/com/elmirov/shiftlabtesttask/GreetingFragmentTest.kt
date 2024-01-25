package com.elmirov.shiftlabtesttask

import androidx.fragment.app.testing.launchFragmentInContainer
import com.elmirov.shiftlabtesttask.screen.GreetingScreen
import com.elmirov.shiftlabtesttask.ui.GreetingFragment
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Before
import org.junit.Test

class GreetingFragmentTest : TestCase() {

    companion object {
        private const val STUB_NAME = "STUB)"
    }

    @Before
    fun setup() {
        launchFragmentInContainer<GreetingFragment>(themeResId = R.style.Base_Theme_ShiftlabTestTask)
    }

    @Test
    fun checkScreenItems() = run {
        GreetingScreen {
            step("Toolbar exist") {
                toolbar {
                    isVisible()
                    hasTitle(R.string.greeting)
                }
            }

            step("Button exist") {
                greeting {
                    isVisible()
                    hasText(R.string.greeting)
                }
            }
            step("Button clickable") {
                greeting {
                    isClickable()
                }
            }
        }
    }

    @Test
    fun checkDialog() = run {
        GreetingScreen {
            step("On button click open dialog") {
                greeting.click()
                dialog.isVisible()
            }

            step("Dialog with right title") {
                dialog.title.hasText(R.string.greeting)
            }
            step("Dialog with right message") {
                val dialogMessageText =
                    String.format(getResourceString(R.string.hello_with_name), STUB_NAME)
                dialog.message.hasText(dialogMessageText)
            }
            step("Dialog has positive button") {
                dialog {
                    positiveButton.isVisible()
                    positiveButton.hasText(R.string.ok)
                    positiveButton.isClickable()
                }
            }
        }
    }
}