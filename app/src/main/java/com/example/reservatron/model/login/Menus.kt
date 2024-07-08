package com.example.reservatron.model.login

typealias LisMenus = ArrayList<Menus>
data class Menus (
    val id: Long,
    val name: String,
    val restaurantID: Long
)