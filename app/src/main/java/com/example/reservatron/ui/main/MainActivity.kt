package com.example.reservatron.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reservatron.R
import com.example.reservatron.databinding.ActivityMainBinding
import com.example.reservatron.repositories.PreferencesRepository
import com.example.reservatron.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        acionarBotonAgregar()
        checkToken()
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupEventListeners() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emileTxt.text.toString()
            val password = binding.passwordTxt.text.toString()
            model.login(email, password, this)
            model.errorMessage.observe(this) { errorMessage ->
                if (errorMessage.isEmpty()) {
                    val intent = Intent(this, ListaRestauranteActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            Toast.makeText(this, "El token es: $token", Toast.LENGTH_SHORT).show()
        }

    }

    private fun acionarBotonAgregar() {
        binding.signInBtn.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, ListaRestauranteActivity::class.java)
            startActivity(intent)
        }
    }





}