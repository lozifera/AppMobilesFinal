package com.example.reservatron.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.RestaurantInsert
import com.example.reservatron.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormRestViewModel: ViewModel(){
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _crearRestaurante: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val crearRestaurante: LiveData<Boolean> get() = _crearRestaurante
    private val _restaurante: MutableLiveData<Restaurant> by lazy {
        MutableLiveData<Restaurant>()
    }
    val restaurante: LiveData<Restaurant> get() = _restaurante
    fun insertRestaurante(
        name: String,
        description: String,
        address: String,
        city: String,
        token : String
    ) {
        if(name.isEmpty() || description.isEmpty() || address.isEmpty() || city.isEmpty()){
            _errorMessage.postValue("Todos los campos son obligatorios")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            val restaurante = RestaurantInsert(name,address,city,description)
            RestauranteRepository.insertRestaurante(
                restaurante,
                token,
                success = {
                    _showLoading.postValue(false)
                    _crearRestaurante.postValue(true)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })

        }
    }
    fun editarRestaurante(id : Int,name: String, description: String, address: String, city: String, token : String){
        if(name.isEmpty() || description.isEmpty() || address.isEmpty() || city.isEmpty()){
            _errorMessage.postValue("Todos los campos son obligatorios")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            val restaurante = RestaurantInsert(name,address,city,description)
            RestauranteRepository.editRestaurant(
                restaurante,
                token,
                id,
                success = {
                    _showLoading.postValue(false)
                    _crearRestaurante.postValue(true)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })

        }
    }
    fun fetchRestauranteDescripcion(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.getRestauranteById(
                id,
                success = { restaurante ->
                    _showLoading.postValue(false)
                    _restaurante.postValue(restaurante)
                },
                failure = { exception ->
                    _errorMessage.postValue(exception.message)
                    _showLoading.postValue(false)
                    exception.printStackTrace()
                }
            )
        }
    }



}