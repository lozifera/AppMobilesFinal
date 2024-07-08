package com.example.reservatron.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reservatron.R
import com.example.reservatron.databinding.ActivityMainBinding
import com.example.reservatron.databinding.ActivityRegistroBinding
import com.example.reservatron.model.login.Registro
import com.example.reservatron.ui.viewModel.RegistroViewModel

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    private val model: RegistroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()

        getRegistroFromInput()
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    fun getRegistroFromInput() {
        binding.RRegistroBtn.setOnClickListener {
            val nombre = binding.RNombreTxt.text.toString()
            val email = binding.REmileTxt.text.toString()
            val password = binding.RPasswordTxt.text.toString()
            val numero = binding.RNumeroTxt.text.toString()
            model.createRegistro(nombre, email, password, numero)
        }

    }


}