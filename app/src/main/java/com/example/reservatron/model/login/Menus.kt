package com.example.reservatron.model.login

import com.squareup.moshi.Json

typealias LisMenus = ArrayList<Menus>
data class Menus (
    val id: Long,
    val name: String,
    @Json(name = "restaurant_id")
    val restaurantID: Long,

    val plates: List<Plato>
)