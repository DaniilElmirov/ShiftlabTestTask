package com.elmirov.shiftlabtesttask.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.shiftlabtesttask.R
import com.elmirov.shiftlabtesttask.ShiftlabApplication
import com.elmirov.shiftlabtesttask.databinding.FragmentRegistrationBinding
import com.elmirov.shiftlabtesttask.presentation.registration.state.ErrorType
import com.elmirov.shiftlabtesttask.presentation.registration.state.RegistrationState
import com.elmirov.shiftlabtesttask.presentation.registration.viewmodel.RegistrationViewModel
import com.elmirov.shiftlabtesttask.presentation.viewmodelfactory.ViewModelFactory
import com.elmirov.shiftlabtesttask.utill.MultiTextWatcher
import com.elmirov.shiftlabtesttask.utill.collectLifecycleFlow
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as ShiftlabApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        addTextChangedListeners()
        applyState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnClickListeners() {
        with(binding) {
            registration.setOnClickListener {
                viewModel.registration(
                    name = binding.nameText.text.toString(),
                    secondName = binding.secondNameText.text.toString(),
                    dateOfBirth = binding.dateOfBirthText.text.toString(),
                    password = binding.passwordText.text.toString(),
                    repeatedPassword = binding.repeatedPasswordText.text.toString(),
                )
            }
        }
    }

    private fun addTextChangedListeners() {
        val editTexts = listOf(
            binding.nameText,
            binding.secondNameText,
            binding.passwordText,
            binding.repeatedPasswordText,
            binding.dateOfBirthText
        )
        val textWatcher = MultiTextWatcher(
            textInputEditTexts = editTexts,
            textChanged = {
                viewModel.reset()
            },
            allFilled = {
                viewModel.allFilled(it)
            }
        )

        editTexts.forEach {
            it.addTextChangedListener(textWatcher)
        }
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.state) {
            when (it) {
                RegistrationState.Initial -> applyInitialState()

                RegistrationState.Filled -> applyFilledState()

                is RegistrationState.InputError -> applyInputErrorState(it.type)
            }
        }
    }

    private fun applyInitialState() {
        with(binding) {
            name.error = null
            secondName.error = null
            dateOfBirth.error = null
            password.error = null
            repeatedPassword.error = null

            registration.isEnabled = false
        }
    }

    private fun applyFilledState() {
        binding.registration.isEnabled = true
    }

    private fun applyInputErrorState(type: ErrorType) {
        with(binding) {

            when (type) {
                ErrorType.Name -> {
                    name.error = getString(R.string.name_help)
                    secondName.error = null
                    dateOfBirth.error = null
                    password.error = null
                    repeatedPassword.error = null
                }

                ErrorType.SecondName -> {
                    name.error = null
                    secondName.error = getString(R.string.second_name_help)
                    dateOfBirth.error = null
                    binding.password.error = null
                    binding.repeatedPassword.error = null
                }

                ErrorType.DateOfBirth -> {
                    name.error = null
                    secondName.error = null
                    dateOfBirth.error = getString(R.string.date_of_birth_help)
                    password.error = null
                    repeatedPassword.error = null
                }

                ErrorType.Password -> {
                    name.error = null
                    secondName.error = null
                    dateOfBirth.error = null
                    password.error = getString(R.string.password_help)
                    repeatedPassword.error = null
                }

                ErrorType.RepeatedPassword -> {
                    name.error = null
                    secondName.error = null
                    dateOfBirth.error = null
                    password.error = null
                    repeatedPassword.error = getString(R.string.repeated_password_help)
                }
            }
        }
    }
}