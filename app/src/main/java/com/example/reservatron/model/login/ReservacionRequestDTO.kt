package com.example.reservatron.model.login

data class ReservacionRequestDTO(
    val restaurant_id: Long,
    val date: String,
    val time: String,
    val people: Long,
    val food: List<Comida>? = null
)