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
import com.elmirov.shiftlabtesttask.presentation.registration.state.RegistrationState
import com.elmirov.shiftlabtesttask.presentation.registration.viewmodel.RegistrationViewModel
import com.elmirov.shiftlabtesttask.presentation.viewmodelfactory.ViewModelFactory
import com.elmirov.shiftlabtesttask.utill.addTextWatcher
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

        addTextChangeListeners()
        subViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subViewModel() {
        collectLifecycleFlow(viewModel.isCorrectName) {
            binding.name.error = if (it) null else getString(R.string.name_help)
        }

        collectLifecycleFlow(viewModel.isCorrectSecondName) {
            binding.secondName.error = if (it) null else getString(R.string.second_name_help)
        }

        collectLifecycleFlow(viewModel.isCorrectDateOfBirth) {
            binding.dateOfBirth.error = if (it) null else getString(R.string.date_of_birth_help)
        }

        collectLifecycleFlow(viewModel.isCorrectPassword) {
            binding.password.error = if (it) null else getString(R.string.password_help)
        }

        collectLifecycleFlow(viewModel.isPasswordConfirmed) {
            binding.repeatedPassword.error =
                if (it) null else getString(R.string.repeated_password_help)
        }

        collectLifecycleFlow(viewModel.state) {
            when (it) {
                //TODO() переделать
                RegistrationState.Content -> Unit

                RegistrationState.Initial -> {
                    binding.registration.setOnClickListener {
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
        }
    }

    private fun addTextChangeListeners() {
        with(binding) {
            nameText.addTextChangedListener(addTextWatcher(viewModel::resetIsCorrectName))
            secondNameText.addTextChangedListener(addTextWatcher(viewModel::resetIsCorrectSecondName))
            dateOfBirthText.addTextChangedListener(addTextWatcher(viewModel::resetIsCorrectDateOfBirth))
            passwordText.addTextChangedListener(addTextWatcher(viewModel::resetIsCorrectPassword))
            repeatedPasswordText.addTextChangedListener(addTextWatcher(viewModel::resetIsPasswordConfirmed))
        }
    }
}