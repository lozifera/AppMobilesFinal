package com.example.reservatron.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservatron.model.login.Restaurantes
import com.example.reservatron.repositories.RestauranteRepository

class ListaRestauranteViewModel: ViewModel(){

    private val _restaurantes : MutableLiveData<Restaurantes> by lazy {
        MutableLiveData<Restaurantes>(Restaurantes())
    }

    val restaurantes: LiveData<Restaurantes> = _restaurantes

    fun fetchListaRestaurantes() {
        RestauranteRepository.getRestaurantes(
            success = { restaurantes ->
                restaurantes?.let {
                    _restaurantes.postValue(ArrayList(it))
                }
            },
            failure = {
                it.printStackTrace()
            })
    }


}