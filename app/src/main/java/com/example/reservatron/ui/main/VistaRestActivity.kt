package com.example.reservatron.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.reservatron.R
import com.example.reservatron.adapter.PhotoAdapter
import com.example.reservatron.databinding.ActivityVistaRestBinding
import com.example.reservatron.ui.viewModel.VistaRestViewModel

class VistaRestActivity : AppCompatActivity(), PhotoAdapter.OnPhotoClickListener{
    lateinit var binding: ActivityVistaRestBinding
    private val model: VistaRestViewModel by viewModels()

    private var idRestaurante: Int = 0
    var containernombre: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVistaRestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        setUpRecyclerView()

        //conseguimos el id del restaurante
        idRestaurante = intent.getIntExtra("restauranteId", -1)
        if (idRestaurante != -1) {
            model.fetchRestauranteDescripcion(idRestaurante)
        } else {
            Toast.makeText(this, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT)
                .show()
        }

    }



    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        model.showLoading.observe(this) {
            if (it) {
               // binding.imgLoading.visibility = View.VISIBLE
            } else {
                //binding.imgLoading.visibility = View.GONE
            }
        }
        model.restaurante.observe(this) {
            if (it == null) {
                return@observe
            }
            idRestaurante = it.id
            binding.vNombreTxt.text = it.name
            binding.vDescripcionTxt.text = it.description
            var ubicacion = "Ubicacion: " + it.address
            var ciudad = "Ciudad: " + it.city
            binding.vDirecionTxt.text = ubicacion
            binding.vCiudadTxt.text = ciudad
            binding.textView.text = "Fotos"
            Glide.with(this)
                .load(it.logo)
                .into(binding.vPhotoImg)
            binding.lstPhoto.adapter = PhotoAdapter(it.photos, this)
            Log.d("RestauranteDescripcionActivity", "Restaurante: $it")
        }
    }
    private fun setUpRecyclerView() {
        binding.lstPhoto.apply {
            layoutManager = LinearLayoutManager(this@VistaRestActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PhotoAdapter(emptyList(), this@VistaRestActivity)
        }
    }
    override fun onResume() {
        super.onResume()
        model.fetchRestauranteDescripcion(idRestaurante)

    }

    override fun onGaleriaClick(imageUrl: String) {
        TODO("Not yet implemented")
    }
}