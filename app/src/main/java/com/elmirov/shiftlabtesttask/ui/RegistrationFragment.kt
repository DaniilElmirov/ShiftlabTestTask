package com.elmirov.shiftlabtesttask.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance(): RegistrationFragment = RegistrationFragment()

        private const val TARGET_DATE_FORMAT = "dd.MM.yyyy"
        private const val TAG_DATE_PICKER = "date_picker"
    }

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

            binding.dateOfBirth.setEndIconOnClickListener {
                showDatePicker()
            }
        }
    }

    private fun showDatePicker() {
        val constraints =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.enter_date_of_birth)
            .setCalendarConstraints(constraints.build())
            .build()

        datePicker.addOnPositiveButtonClickListener { date ->
            val dateFormat = SimpleDateFormat(TARGET_DATE_FORMAT, Locale.getDefault())
            val selectedDate = dateFormat.format(Date(date))
            binding.dateOfBirthText.setText(selectedDate)
        }

        datePicker.show(parentFragmentManager, TAG_DATE_PICKER)
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

                RegistrationState.Loading -> applyLoadingState()
            }
        }
    }

    private fun applyInitialState() {
        with(binding) {
            content.isVisible = true
            progressBar.isVisible = false

            name.error = null
            secondName.error = null
            dateOfBirth.error = null
            password.error = null
            repeatedPassword.error = null

            registration.text = getString(R.string.fill_all_fields)
            registration.isEnabled = false
        }
    }

    private fun applyFilledState() {
        with(binding) {
            content.isVisible = true
            progressBar.isVisible = false

            registration.text = getString(R.string.registration)
            registration.isEnabled = true
        }
    }

    private fun applyInputErrorState(type: ErrorType) {
        with(binding) {
            content.isVisible = true
            progressBar.isVisible = false

            when (type) {
                ErrorType.NameLength -> {
                    val message = getString(R.string.name_help)
                    setErrorMessageTo(name, message)

                    content.scrollTo(name.x.toInt(), name.y.toInt())
                }

                ErrorType.SecondNameLength -> {
                    val message = getString(R.string.second_name_help)
                    setErrorMessageTo(secondName, message)

                    content.scrollTo(secondName.x.toInt(), secondName.y.toInt())
                }

                ErrorType.DateWrongFormat -> {
                    val message = getString(R.string.date_of_birth_help)
                    setErrorMessageTo(dateOfBirth, message)

                    content.scrollTo(dateOfBirth.x.toInt(), dateOfBirth.y.toInt())
                }

                ErrorType.FutureDate -> {
                    val message = getString(R.string.future_date_help)
                    setErrorMessageTo(dateOfBirth, message)

                    content.scrollTo(dateOfBirth.x.toInt(), dateOfBirth.y.toInt())
                }

                ErrorType.SimplePassword -> {
                    val message = getString(R.string.password_help)
                    setErrorMessageTo(password, message)

                    content.scrollTo(password.x.toInt(), password.y.toInt())
                }

                ErrorType.NoMatchPassword -> {
                    val message = getString(R.string.repeated_password_help)
                    setErrorMessageTo(repeatedPassword, message)

                    content.scrollTo(repeatedPassword.x.toInt(), repeatedPassword.y.toInt())
                }
            }
        }
    }

    private fun applyLoadingState() {
        with(binding) {
            content.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun setErrorMessageTo(to: TextInputLayout, message: String) {
        val textInputLayouts = arrayOf(
            binding.name,
            binding.secondName,
            binding.password,
            binding.repeatedPassword,
            binding.dateOfBirth
        )

        textInputLayouts.forEach {
            if (it == to)
                it.error = message
            else
                it.error = null
        }
    }
}