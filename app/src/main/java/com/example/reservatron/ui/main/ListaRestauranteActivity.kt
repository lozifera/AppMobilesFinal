package com.example.reservatron.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservatron.R
import com.example.reservatron.adapter.RestauranteAdapter
import com.example.reservatron.databinding.ActivityListaRestauranteBinding
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.ui.viewModel.ListaRestauranteViewModel

class ListaRestauranteActivity : AppCompatActivity(), RestauranteAdapter.OnRestaurantClickListener{
    lateinit var binding: ActivityListaRestauranteBinding
    private val model: ListaRestauranteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListaRestauranteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewModelObservers()
        model.fetchListaRestaurantes()
    }

    private fun setupRecyclerView() {
        binding.lstRestaurantes.apply {
            layoutManager = LinearLayoutManager(this@ListaRestauranteActivity)
            adapter = RestauranteAdapter(arrayListOf(),this@ListaRestauranteActivity)
        }
    }

    private fun setupViewModelObservers() {
        model.restaurantes.observe(this){
            val lst = binding.lstRestaurantes
            lst.adapter = RestauranteAdapter(it,this)
        }
    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        Log.d("rest", restaurant.id.toString())
        val intent = Intent(this,VistaRestActivity ::class.java)
        intent.putExtra("restauranteId",restaurant.id)
        startActivity(intent)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itmCrearR) {
            val intent = Intent(this, FormRestActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}