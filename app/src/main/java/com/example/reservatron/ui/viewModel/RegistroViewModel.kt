package com.example.reservatron.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservatron.model.login.Registro
import com.example.reservatron.repositories.UserRepository

class RegistroViewModel: ViewModel(){


    val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
        fun createRegistro(
            nombre: String,
            email: String,
            password: String,
            numero: String) {
            val registro = Registro(
                name = nombre,
                email = email,
                password = password,
                phone = numero
            )
            UserRepository.doRegister(
                registro,
                success = {

                    println("Usuario registrado")
                    _closeActivity.value = true

                },
                failure = {
                    println("Usuario no registrado")
                })
        }


}