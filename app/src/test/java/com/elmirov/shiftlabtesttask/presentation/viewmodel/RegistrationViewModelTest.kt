package com.elmirov.shiftlabtesttask.presentation.viewmodel

import app.cash.turbine.test
import com.elmirov.shiftlabtesttask.domain.usecase.RegistrationUseCase
import com.elmirov.shiftlabtesttask.navigation.router.RegistrationRouter
import com.elmirov.shiftlabtesttask.presentation.registration.state.ErrorType
import com.elmirov.shiftlabtesttask.presentation.registration.state.RegistrationState
import com.elmirov.shiftlabtesttask.presentation.registration.viewmodel.RegistrationViewModel
import com.elmirov.shiftlabtesttask.util.ProjectData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.stream.Stream

class RegistrationViewModelTest {

    private val registrationUseCase: RegistrationUseCase = mock()
    private val registrationRouter: RegistrationRouter = mock()

    private val viewModel = RegistrationViewModel(registrationUseCase, registrationRouter)


    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when started EXPECT initial state`() = runTest {
        viewModel.state.test {
            assertEquals(RegistrationState.Initial, awaitItem())
        }
    }

    @Test
    fun `registration with valid data EXPECT state loading`() = runTest {
        viewModel.registration(
            name = ProjectData.rightName,
            secondName = ProjectData.rightSecondName,
            dateOfBirth = ProjectData.rightDateOfBirth,
            password = ProjectData.rightPassword,
            repeatedPassword = ProjectData.rightRepeatedPassword,
        )

        viewModel.state.test {
            assertEquals(RegistrationState.Loading, awaitItem())
        }
    }

    @Test
    fun `registration with valid data EXPECT registration`() = runTest {
        viewModel.registration(
            name = ProjectData.rightName,
            secondName = ProjectData.rightSecondName,
            dateOfBirth = ProjectData.rightDateOfBirth,
            password = ProjectData.rightPassword,
            repeatedPassword = ProjectData.rightRepeatedPassword,
        )

        verify(registrationUseCase).invoke(ProjectData.user)
    }

    @Test
    fun `registration with valid data EXPECT open greeting`() = runTest {
        viewModel.registration(
            name = ProjectData.rightName,
            secondName = ProjectData.rightSecondName,
            dateOfBirth = ProjectData.rightDateOfBirth,
            password = ProjectData.rightPassword,
            repeatedPassword = ProjectData.rightRepeatedPassword,
        )

        verify(registrationRouter).openGreeting()
    }

    @Test
    fun `reset EXPECT state initial`() = runTest {
        viewModel.reset()

        viewModel.state.test {
            assertEquals(RegistrationState.Initial, awaitItem())
        }
    }

    @Test
    fun `all filled EXPECT state filled`() = runTest {
        viewModel.allFilled(true)

        viewModel.state.test {
            assertEquals(RegistrationState.Filled, awaitItem())
        }
    }

    @Test
    fun `not all filled EXPECT state initial`() = runTest {
        viewModel.allFilled(false)

        viewModel.state.test {
            assertEquals(RegistrationState.Initial, awaitItem())
        }
    }

    companion object {
        @JvmStatic
        fun allInputError(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    ProjectData.wrongName,
                    ProjectData.rightSecondName,
                    ProjectData.rightDateOfBirth,
                    ProjectData.rightPassword,
                    ProjectData.rightRepeatedPassword,
                    RegistrationState.InputError(ErrorType.NameLength)
                ),

                Arguments.of(
                    ProjectData.rightName,
                    ProjectData.wrongSecondName,
                    ProjectData.rightDateOfBirth,
                    ProjectData.rightPassword,
                    ProjectData.rightRepeatedPassword,
                    RegistrationState.InputError(ErrorType.SecondNameLength)
                ),

                Arguments.of(
                    ProjectData.rightName,
                    ProjectData.rightSecondName,
                    ProjectData.wrongDateOfBirthFormat,
                    ProjectData.rightPassword,
                    ProjectData.rightRepeatedPassword,
                    RegistrationState.InputError(ErrorType.DateWrongFormat)
                ),

                Arguments.of(
                    ProjectData.rightName,
                    ProjectData.rightSecondName,
                    ProjectData.wrongDateOfBirthFuture,
                    ProjectData.rightPassword,
                    ProjectData.rightRepeatedPassword,
                    RegistrationState.InputError(ErrorType.FutureDate)
                ),

                Arguments.of(
                    ProjectData.rightName,
                    ProjectData.rightSecondName,
                    ProjectData.rightDateOfBirth,
                    ProjectData.wrongPassword,
                    ProjectData.rightRepeatedPassword,
                    RegistrationState.InputError(ErrorType.SimplePassword)
                ),

                Arguments.of(
                    ProjectData.rightName,
                    ProjectData.rightSecondName,
                    ProjectData.rightDateOfBirth,
                    ProjectData.rightPassword,
                    ProjectData.wrongRepeatedPassword,
                    RegistrationState.InputError(ErrorType.NoMatchPassword)
                ),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("allInputError")
    fun `registration with wrong parameters EXPECT state input error`(
        name: String,
        secondName: String,
        dateOfBirth: String,
        password: String,
        repeatedPassword: String,
        expected: RegistrationState.InputError,
    ) = runTest {
        viewModel.registration(name, secondName, dateOfBirth, password, repeatedPassword)

        viewModel.state.test {
            assertEquals(expected, awaitItem())
        }
    }
}