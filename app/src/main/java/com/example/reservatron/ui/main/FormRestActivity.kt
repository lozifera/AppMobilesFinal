package com.example.reservatron.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reservatron.R
import com.example.reservatron.databinding.ActivityFormRestBinding
import com.example.reservatron.repositories.PreferencesRepository
import com.example.reservatron.ui.viewModel.FormRestViewModel
import io.jsonwebtoken.Jwts

class FormRestActivity : AppCompatActivity() {
    lateinit var binding: ActivityFormRestBinding
    private val model: FormRestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormRestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()



    }
    fun getUserIdFromToken(token: String): Long {
        val claims = Jwts.parser().setSigningKey("tu_clave_secreta").parseClaimsJws(token).body
        return claims["id"] as Long
    }

    private fun setupEventListeners() {
        binding.nuGuardarBtn.setOnClickListener {
            val token = PreferencesRepository.getToken(this)
            if (token != null) {
                Toast.makeText(this, "El token es: $token", Toast.LENGTH_SHORT).show()
            }

            val nombre = binding.nuNombreTxt.text.toString()
            val direccion = binding.nuDirecionTxt.text.toString()
            val ciudad = binding.nuCiudadTxt.text.toString()
            val descripcion = binding.nuDescripcionTxt.text.toString()

            if (token != null) {
                model.insertRestaurante(token, nombre, direccion, ciudad, descripcion)
            }
        }

    }


}