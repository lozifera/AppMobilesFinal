package com.example.reservatron.model.login

data class Plato (
    val id: Long,
    val name: String,
    val description: String,
    val price: String,
    val menuCategoryID: Long,
    val menuCategory: LisMenus
)