package com.example.reservatron.model.login

import com.squareup.moshi.Json

typealias Restaurantes = ArrayList<Restaurant>

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val description: String,

    @Json(name = "user_id")
    val userID: Long,
    val logo: String,
    val owner: Owner?,
    val photos: List<Photo>
)
