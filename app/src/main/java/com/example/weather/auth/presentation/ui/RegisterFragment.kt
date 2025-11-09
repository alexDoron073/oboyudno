package com.example.weather.auth.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weather.auth.presentation.viewmodel.RegisterViewModel
import com.example.weather.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupButtons()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupButtons() {
        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text?.toString() ?: ""
            val email = binding.emailEditText.text?.toString() ?: ""
            val password = binding.passwordEditText.text?.toString() ?: ""
            val confirmPassword = binding.confirmPasswordEditText.text?.toString() ?: ""
            viewModel.register(username, email, password, confirmPassword)
        }

        binding.goToLoginButton.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.registerButton.isEnabled = !state.isLoading

                if (state.error != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.error
                } else {
                    binding.errorText.visibility = View.GONE
                }

                if (state.isSuccess) {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToWeatherFragment())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

