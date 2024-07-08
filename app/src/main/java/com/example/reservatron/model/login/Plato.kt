package com.example.reservatron.model.login

import com.squareup.moshi.Json

data class Plato (
    val id: Long,
    val name: String,
    val description: String,
    val price: String,

    @Json(name = "menu_category_id")
    val menuCategoryID: Long
)