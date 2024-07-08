package com.example.reservatron.model.login

import com.squareup.moshi.Json

data class ReservacionResponseDTO (
    val id: Long,

    @Json(name = "user_id")
    val userID: Long,

    @Json(name = "restaurant_id")
    val restaurantID: Long,

    val date: String,
    val time: String,
    val people: Long,
    val status: String,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String,

    val user: User,
    val restaurant: Restaurant,
    val plates: List<PlateElement>
)

data class PlateElement (
    val id: Long,
    val quantity: Long,
    val plate: Plato
)