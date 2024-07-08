package com.example.reservatron.ui.main

import android.content.Intent
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
        setUpViewModelObservers()

        //conseguimos el id del restaurante
        val idRestaurante = intent.getIntExtra("restauranteId", -1)
        if (idRestaurante != -1) {
            model.fetchRestauranteDescripcion(idRestaurante)
        } else {
            Toast.makeText(this, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT)


        }
    }

    private fun setUpViewModelObservers() {
        model.errorMessage.observe(this, {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        model.showLoading.observe(this, {
            if (it) {
                binding.nuGuardarBtn.isEnabled = false
                //binding.nuProgressBar.show()
            } else {
                binding.nuGuardarBtn.isEnabled = true
                // binding.nuProgressBar.hide()
            }
        })

        model.crearRestaurante.observe(this, {
            if (it) {
                Toast.makeText(this, "Restaurante creado", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        // Observar los datos del restaurante
        model.restaurante.observe(this, { restaurante ->
            // Mostrar los datos del restaurante en los campos de texto
            binding.nuNombreTxt.setText(restaurante.name)
            binding.nuDirecionTxt.setText(restaurante.address)
            binding.nuCiudadTxt.setText(restaurante.city)
            binding.nuDescripcionTxt.setText(restaurante.description)
        })
    }


    private fun setupEventListeners() {

        binding.nuGuardarBtn.setOnClickListener {
            val nombre = binding.nuNombreTxt.text.toString()
            val direccion = binding.nuDirecionTxt.text.toString()
            val ciudad = binding.nuCiudadTxt.text.toString()
            val descripcion = binding.nuDescripcionTxt.text.toString()
            val token = PreferencesRepository.getToken(this)?.let { it1 -> it1 } ?: ""

            if (nombre.isEmpty() || direccion.isEmpty() || ciudad.isEmpty() || descripcion.isEmpty() || token.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                // Obtener el ID del restaurante del Intent
                val id = intent.getIntExtra("restauranteId", -1)

                // Si el ID es diferente de -1, entonces estamos editando un restaurante existente
                if (id != -1) {
                    // Llamar a la función editarRestaurante en el ViewModel
                    model.editarRestaurante(id, nombre, direccion, ciudad, descripcion, token)
                } else {
                    // Si el ID es -1, entonces estamos creando un nuevo restaurante
                    model.insertRestaurante(nombre, direccion, ciudad, descripcion, token)
                }
            }
        }
        binding.nuBtn.setOnClickListener {
            val intent = Intent(this, SubirImagenActivity::class.java)
            startActivity(intent)
        }
        binding.nuLogoBtn.setOnClickListener {
            val intent = Intent(this, SubirImagenActivity::class.java)
            startActivity(intent)
        }

    }


}