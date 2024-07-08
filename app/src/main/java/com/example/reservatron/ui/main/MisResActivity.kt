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
import com.example.reservatron.adapter.misRestaurantesAdapter
import com.example.reservatron.databinding.ActivityMisResBinding
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.repositories.PreferencesRepository
import com.example.reservatron.ui.viewModel.MisResViewModel

class MisResActivity : AppCompatActivity(),misRestaurantesAdapter.OnMisRestauranteClickListener {
    lateinit var binding: ActivityMisResBinding
    private val model: MisResViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMisResBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpRecyclerView()
        setupViewModelObservers()
        model.fetchListaRestaurantes(PreferencesRepository.getToken(this)!!)
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        model.showLoading.observe(this) {
            if (it) {
                //binding.imgLoading.visibility = View.VISIBLE
            } else {
                //binding.imgLoading.visibility = View.GONE
            }
        }
        model.restaurantes.observe(this) {
           val lstRe =binding.listMisRest
            lstRe.adapter = misRestaurantesAdapter(it,this)
        }
        model.deleteSuccess.observe(this){
            if(it){
                Toast.makeText(this,"Restaurante eliminado",Toast.LENGTH_LONG).show()
                model.fetchListaRestaurantes(PreferencesRepository.getToken(this)!!)
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.listMisRest.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MisResActivity)
            adapter = misRestaurantesAdapter(arrayListOf(),this@MisResActivity)
        }
    }

    override fun onRestauranteClick(restaurante: Restaurant) {
        val intent = Intent(this,VistaRestActivity::class.java)
        intent.putExtra("restauranteId",restaurante.id)
        startActivity(intent)
    }

    override fun editarRestaurante(restaurante: Restaurant) {
        val intent = Intent(this,FormRestActivity::class.java)
        intent.putExtra("restauranteId",restaurante.id)
        startActivity(intent)
    }

    override fun eliminarRestaurante(restaurante: Restaurant) {
        model.deleteRestaurante(PreferencesRepository.getToken(this)!!,restaurante.id)
    }
}