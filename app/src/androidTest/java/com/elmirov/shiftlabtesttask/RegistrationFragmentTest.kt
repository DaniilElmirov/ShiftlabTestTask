package com.elmirov.shiftlabtesttask

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.action.GeneralLocation
import com.elmirov.shiftlabtesttask.screen.RegistrationScreen
import com.elmirov.shiftlabtesttask.ui.RegistrationFragment
import com.elmirov.shiftlabtesttask.utill.InputData
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

    @Test
    fun setRightUserInputAllowRegistration() = run {
        RegistrationScreen {
            step("Set name") {
                name.edit {
                    replaceText(InputData.rightName)
                    hasText(InputData.rightName)
                }
            }

            step("Set second name") {
                secondName.edit {
                    replaceText(InputData.rightSecondName)
                    hasText(InputData.rightSecondName)
                }
            }

            step("Set date of birth") {
                dateOfBirth.edit {
                    replaceText(InputData.rightDateOfBirth)
                    hasText(InputData.rightDateOfBirth)
                }
            }

            step("Set password") {
                password.edit {
                    replaceText(InputData.rightPassword)
                    hasText(InputData.rightPassword)
                }
            }

            step("Set repeated password") {
                repeatedPassword.edit {
                    replaceText(InputData.rightRepeatedPassword)
                    hasText(InputData.rightRepeatedPassword)
                }
            }

            step("Allow registration") {
                registration {
                    isEnabled()
                    hasText(R.string.registration)
                }
            }
        }
    }

    @Test
    fun checkNameErrorMessage() = run {
        RegistrationScreen {
            step("Set wrong name") {
                name.edit.replaceText(InputData.wrongName)
            }

            step("Set right data in other edits") {
                secondName.edit.replaceText(InputData.rightSecondName)
                dateOfBirth.edit.replaceText(InputData.rightDateOfBirth)
                password.edit.replaceText(InputData.rightPassword)
                repeatedPassword.edit.replaceText(InputData.rightRepeatedPassword)
            }

            step("Start registration") {
                registration.click()
            }

            step("Name has error") {
                name.hasError(R.string.name_help)
            }
        }
    }

    @Test
    fun checkSecondNameErrorMessage() = run {
        RegistrationScreen {
            step("Set wrong second name") {
                secondName.edit.replaceText(InputData.wrongSecondName)
            }

            step("Set right data in other edits") {
                name.edit.replaceText(InputData.rightName)
                dateOfBirth.edit.replaceText(InputData.rightDateOfBirth)
                password.edit.replaceText(InputData.rightPassword)
                repeatedPassword.edit.replaceText(InputData.rightRepeatedPassword)
            }

            step("Start registration") {
                registration.click()
            }

            step("Second name has error") {
                secondName.hasError(R.string.second_name_help)
            }
        }
    }

    @Test
    fun checkDateOfBirthErrorMessage() = run {
        RegistrationScreen {
            step("Set wrong date of birth: format") {
                dateOfBirth.edit.replaceText(InputData.wrongDateOfBirthFormat)
            }

            step("Set right data in other edits") {
                name.edit.replaceText(InputData.rightName)
                secondName.edit.replaceText(InputData.rightSecondName)
                password.edit.replaceText(InputData.rightPassword)
                repeatedPassword.edit.replaceText(InputData.rightRepeatedPassword)
            }

            step("Start registration") {
                registration.click()
            }

            step("Date of birth has format error") {
                dateOfBirth.hasError(R.string.date_of_birth_help)
            }

            step("Set wrong date of birth: future date") {
                dateOfBirth.edit.replaceText(InputData.wrongDateOfBirthFuture)
            }

            step("Start registration") {
                registration.click()
            }

            step("Date of birth has future error") {
                dateOfBirth.hasError(R.string.future_date_help)
            }
        }
    }

    @Test
    fun checkPasswordErrorMessage() = run {
        RegistrationScreen {
            step("Set wrong password") {
                password.edit.replaceText(InputData.wrongPassword)
            }

            step("Set right data in other edits") {
                name.edit.replaceText(InputData.rightName)
                secondName.edit.replaceText(InputData.rightSecondName)
                dateOfBirth.edit.replaceText(InputData.rightDateOfBirth)
                repeatedPassword.edit.replaceText(InputData.rightRepeatedPassword)
            }

            step("Start registration") {
                registration.click()
            }

            step("Password has error") {
                password.hasError(R.string.password_help)
            }
        }
    }

    @Test
    fun checkRepeatedPasswordErrorMessage() = run {
        RegistrationScreen {
            step("Set wrong repeated password") {
                repeatedPassword.edit.replaceText(InputData.wrongRepeatedPassword)
            }

            step("Set right data in other edits") {
                name.edit.replaceText(InputData.rightName)
                secondName.edit.replaceText(InputData.rightSecondName)
                dateOfBirth.edit.replaceText(InputData.rightDateOfBirth)
                password.edit.replaceText(InputData.rightPassword)
            }

            step("Start registration") {
                registration.click()
            }

            step("Repeated password has error") {
                repeatedPassword.hasError(R.string.repeated_password_help)
            }
        }
    }

    @Test
    fun dateIconClickOpenDatePicker() = run {
        RegistrationScreen {
            step("On date icon click") {
                dateOfBirth.click(GeneralLocation.CENTER_RIGHT)
            }

            step("Date picker visible") {
                datePicker.isVisible()
            }
        }
    }

    @Test
    fun afterRegistrationShowOnlyProgressBar() = run {
        RegistrationScreen {
            step("Before registration progress bar gone") {
                progressBar.isGone()
            }

            step("Set right data into fields")  {
                name.edit.replaceText(InputData.rightName)
                secondName.edit.replaceText(InputData.rightSecondName)
                dateOfBirth.edit.replaceText(InputData.rightDateOfBirth)
                password.edit.replaceText(InputData.rightPassword)
                repeatedPassword.edit.replaceText(InputData.rightRepeatedPassword)
            }

            step("Registration") {
                registration.click()
            }

            step("Content gone") {
                content.isGone()
            }

            step("Progress bar visible") {
                progressBar.isVisible()
            }
        }
    }
}