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
import com.elmirov.shiftlabtesttask.databinding.FragmentGreetingBinding
import com.elmirov.shiftlabtesttask.presentation.greeting.state.GreetingState
import com.elmirov.shiftlabtesttask.presentation.greeting.viewmodel.GreetingViewModel
import com.elmirov.shiftlabtesttask.presentation.viewmodelfactory.ViewModelFactory
import com.elmirov.shiftlabtesttask.utill.collectLifecycleFlow
import com.elmirov.shiftlabtesttask.utill.showDialog
import javax.inject.Inject

class GreetingFragment : Fragment() {

    companion object {
        fun newInstance(): GreetingFragment = GreetingFragment()
    }

    private var _binding: FragmentGreetingBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GreetingViewModel::class.java]
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
        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        applyState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.greeting.setOnClickListener {
            viewModel.greeting()
        }
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.state) {
            when (it) {
                is GreetingState.Content -> applyContentState(it.name)

                GreetingState.Initial -> applyInitialState()
            }
        }
    }

    private fun applyContentState(name: String) {
        binding.greeting.isVisible = false

        showDialog(
            context = this.requireContext(),
            onClick = {
                viewModel.closeGreeting()
            },
            title = getString(R.string.greeting),
            message = String.format(getString(R.string.hello_with_name), name)
        )
    }

    private fun applyInitialState() {
        binding.greeting.isVisible = true
    }
}