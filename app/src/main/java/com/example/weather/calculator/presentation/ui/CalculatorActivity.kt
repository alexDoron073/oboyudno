package com.example.weather.calculator.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weather.R
import com.example.weather.calculator.presentation.viewmodel.CalculatorViewModel
import com.example.weather.databinding.ActivityCalculatorBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private val viewModel: CalculatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupButtons()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.calculator)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupButtons() {
        binding.button0.setOnClickListener { viewModel.appendToExpression("0") }
        binding.button1.setOnClickListener { viewModel.appendToExpression("1") }
        binding.button2.setOnClickListener { viewModel.appendToExpression("2") }
        binding.button3.setOnClickListener { viewModel.appendToExpression("3") }
        binding.button4.setOnClickListener { viewModel.appendToExpression("4") }
        binding.button5.setOnClickListener { viewModel.appendToExpression("5") }
        binding.button6.setOnClickListener { viewModel.appendToExpression("6") }
        binding.button7.setOnClickListener { viewModel.appendToExpression("7") }
        binding.button8.setOnClickListener { viewModel.appendToExpression("8") }
        binding.button9.setOnClickListener { viewModel.appendToExpression("9") }
        binding.buttonAdd.setOnClickListener { viewModel.appendToExpression("+") }
        binding.buttonSubtract.setOnClickListener { viewModel.appendToExpression("-") }
        binding.buttonMultiply.setOnClickListener { viewModel.appendToExpression("*") }
        binding.buttonDivide.setOnClickListener { viewModel.appendToExpression("/") }
        binding.buttonDot.setOnClickListener { viewModel.appendToExpression(".") }
        binding.buttonEquals.setOnClickListener { viewModel.calculate() }
        binding.buttonClear.setOnClickListener { viewModel.clear() }
        binding.buttonDelete.setOnClickListener { viewModel.deleteLast() }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.expressionText.text = state.expression
                binding.resultText.text = state.result
                
                if (state.error != null) {
                    binding.errorText.visibility = android.view.View.VISIBLE
                    binding.errorText.text = state.error
                } else {
                    binding.errorText.visibility = android.view.View.GONE
                }
            }
        }
    }
}

