package com.example.reservatron.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VistaRestViewModel: ViewModel(){
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    val _restaurante: MutableLiveData<Restaurant> by lazy {
        MutableLiveData<Restaurant>(null)
    }
    val restaurante: LiveData<Restaurant> get() = _restaurante

    fun fetchRestauranteDescripcion(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.getRestauranteById(
                id = id,
                success = {
                    _restaurante.postValue(it)
                    _showLoading.postValue(false)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                }
            )

        }
    }
}