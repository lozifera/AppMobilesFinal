package com.example.reservatron.repositories

import android.util.Log
import com.example.reservatron.api.APIautenticacion
import com.example.reservatron.model.login.LoginRequestDTO
import com.example.reservatron.model.login.LoginResponseDTO
import com.example.reservatron.model.login.Registro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    fun doLogin(
        email: String,
        password: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIautenticacion =
            retrofit.create(APIautenticacion::class.java)
        service.login(LoginRequestDTO(email, password))
            .enqueue(object : Callback<LoginResponseDTO> {
                override fun onResponse(
                    res: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    if (response.code() == 401) {
                        success(null)
                        return
                    }
                    val loginResponse = response.body()
                    success(loginResponse)
                }

                override fun onFailure(res: Call<LoginResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }
    fun doRegister(
        registro: Registro,
        success: (Registro) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIautenticacion =
            retrofit.create(APIautenticacion::class.java)
        service.register(registro).enqueue(object : Callback<Registro> {
            override fun onResponse(res: Call<Registro>, response: Response<Registro>) {
                val registerResponse = response.body()
                Log.d("UsuarioRegistrado", registro.toString())
                success(registerResponse!!)
            }

            override fun onFailure(res: Call<Registro>, t: Throwable) {
                failure(t)
                Log.d("UsuarioNoRegistrado", registro.toString())
            }
        })
    }
}