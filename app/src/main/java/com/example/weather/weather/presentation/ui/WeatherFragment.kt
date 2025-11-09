package com.example.weather.weather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.weather.domain.model.WeatherDay
import com.example.weather.weather.presentation.ui.adapter.WeatherAdapter
import com.example.weather.weather.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherAdapter: WeatherAdapter
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter { weatherDay ->
            val action = WeatherFragmentDirections.actionWeatherFragmentToWeatherDetailFragment(weatherDay)
            findNavController().navigate(action)
        }
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.weatherRecyclerView.adapter = weatherAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.weatherRecyclerView.visibility = if (state.weatherDays.isNotEmpty()) View.VISIBLE else View.GONE
                
                if (state.weatherDays.isNotEmpty()) {
                    weatherAdapter.submitList(state.weatherDays)
                }
                
                if (state.error != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.errorText.text = "${getString(R.string.error_loading)}: ${state.error}"
                } else {
                    binding.errorText.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                }
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadWeatherData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

