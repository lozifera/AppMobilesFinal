package com.example.reservatron.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.RestaurantInsert
import com.example.reservatron.repositories.RestauranteRepository

class FormRestViewModel: ViewModel(){

    val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
    fun insertRestaurante(
        token: String,
        nombre: String,
        direccion: String,
        ciudad: String,
        descripcion: String
    ){
        val restaurante = RestaurantInsert(
            name = nombre,
            address = direccion,
            city = ciudad,
            description = descripcion
        )
        RestauranteRepository.insertRestaurante(
            token,
            restaurante,
            success = {
                println("Restaurante registrado")
                _closeActivity.value = true
            },
            failure = {
                println("Restaurante no registrado")
            })
    }



}