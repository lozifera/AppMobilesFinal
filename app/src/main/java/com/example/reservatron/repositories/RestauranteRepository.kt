package com.example.reservatron.repositories

import com.example.reservatron.api.APIrestaurantes
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.RestaurantInsert
import com.example.reservatron.model.login.Restaurantes
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response
import java.util.ArrayList

object RestauranteRepository {
    fun getRestaurantes(success: (Restaurantes?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIrestaurantes =
            retrofit.create(APIrestaurantes::class.java)

        service.ListaRestaurantes().enqueue(object : Callback<Restaurantes> {
            override fun onResponse(res: Call<Restaurantes>, response: Response<Restaurantes>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Restaurantes>, t: Throwable) {
                failure(t)
            }
        })
    }
    fun getRestauranteById(id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIrestaurantes =
            retrofit.create(APIrestaurantes::class.java)

        service.getRestauranteById(id).enqueue(object : Callback<Restaurant?> {
            override fun onResponse(call: Call<Restaurant?>, response: Response<Restaurant?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurant?>, t: Throwable) {
                failure(t)
            }
        })
    }
    fun insertRestaurante(
        token: String,
        restaurante: RestaurantInsert,
        success: (Restaurant?) -> Unit,
        failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)

        service.insertRestaurante(token, restaurante).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Exception("Error en la respuesta del servidor"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }



}




