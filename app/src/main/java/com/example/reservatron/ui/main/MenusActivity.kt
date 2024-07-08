package com.example.reservatron.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservatron.R
import com.example.reservatron.adapter.ManuAdapter
import com.example.reservatron.adapter.PlatoAdapter
import com.example.reservatron.databinding.ActivityMenusBinding
import com.example.reservatron.model.login.Comida
import com.example.reservatron.model.login.Menus
import com.example.reservatron.model.login.Plato
import com.example.reservatron.ui.viewModel.MenusViewModel

class MenusActivity : AppCompatActivity(), ManuAdapter.OnMenuClickListener  , PlatoAdapter.OnPlatoClickListener{
    lateinit var binding: ActivityMenusBinding
    private val model: MenusViewModel by viewModels()
    private var comida = ArrayList<Comida>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        setUpRecyclerView()
        val idRestaurante = intent.getIntExtra("restauranteId", -1)
        if (idRestaurante == -1) {
            // Manejar el caso en que no se pudo obtener el id del restaurante
            Toast.makeText(this, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT).show()
        } else {
            // Usar el id del restaurante
            // Por ejemplo, puedes pasarlo al ViewModel para obtener los menús del restaurante
            model.fetchListaMenus(idRestaurante)
        }

    }

    private fun setUpRecyclerView() {

        binding.rvMenuCat.apply {
            layoutManager = LinearLayoutManager(this@MenusActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ManuAdapter(arrayListOf(), this@MenusActivity)
        }

        binding.rvMenuPlato.apply {
            layoutManager = LinearLayoutManager(this@MenusActivity, LinearLayoutManager.VERTICAL, false)
            adapter = PlatoAdapter(arrayListOf(),true,comida, this@MenusActivity) // Replace 'myComidaList' with your actual ArrayList<Comida>
        }
    }

    private fun setupViewModelObservers() {
        model.menus.observe(this) {
            val lstMenu = binding.rvMenuCat
            lstMenu.adapter = ManuAdapter(it, this)
        }
    }

    override fun onMenuClick(menu: Menus) {

    }

    override fun botonAumentar(plato: Plato) {
        TODO("Not yet implemented")
    }

    override fun botonDisminuir(plato: Plato) {
        TODO("Not yet implemented")
    }
}