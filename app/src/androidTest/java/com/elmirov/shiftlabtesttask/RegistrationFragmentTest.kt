package com.elmirov.shiftlabtesttask

import androidx.fragment.app.testing.launchFragmentInContainer
import com.elmirov.shiftlabtesttask.screen.RegistrationScreen
import com.elmirov.shiftlabtesttask.ui.RegistrationFragment
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Before
import org.junit.Test

class RegistrationFragmentTest : TestCase() {

    @Before
    fun setup() {
        launchFragmentInContainer<RegistrationFragment>(themeResId = R.style.Base_Theme_ShiftlabTestTask)
    }

    @Test
    fun checkToolBar() {
        RegistrationScreen {
            toolbar {
                isVisible()
                hasTitle(R.string.registration)
            }
        }
    }

    @Test
    fun checkRegistrationButton() {
        RegistrationScreen {
            registration {
                isVisible()
                hasText(R.string.fill_all_fields)
                isDisabled()
            }
        }
    }

    @Test
    fun checkTextInputLayouts() = run {
        RegistrationScreen {
            step("Name layout with hint") {
                name {
                    isVisible()
                    hasHint(R.string.enter_name)
                }
            }

            step("Second name layout with hint") {
                secondName {
                    isVisible()
                    hasHint(R.string.enter_second_name)
                }
            }

            step("Date of birth layout with hint") {
                dateOfBirth {
                    isVisible()
                    hasHint(R.string.enter_date_of_birth)
                }
            }

            step("Password layout with hint") {
                password {
                    isVisible()
                    hasHint(R.string.enter_password)
                }
            }

            step("Repeated password name layout with hint") {
                repeatedPassword {
                    isVisible()
                    hasHint(R.string.confirm_password)
                }
            }
        }
    }

    @Test
    fun checkEditInLayouts() = run {
        RegistrationScreen {
            val edits = arrayOf(
                Pair("Name edit", name.edit),
                Pair("Second name edit", secondName.edit),
                Pair("Date of birth edit", dateOfBirth.edit),
                Pair("Password edit", password.edit),
                Pair("Repeated password edit", repeatedPassword.edit),
            )

            edits.forEach { pair ->
                step("${pair.first} with empty text") {
                    pair.second {
                        isVisible()
                        hasEmptyText()
                    }
                }
            }
        }
    }
}