package com.example.reservatron.api


import com.example.reservatron.model.login.LisMenus
import com.example.reservatron.model.login.Photo
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.RestaurantInsert
import com.example.reservatron.model.login.Restaurantes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface APIrestaurantes {

    //un poste donde tenemos que llamar una lista de restaurantes
    @POST("restaurants/search")
    fun ListaRestaurantes(): Call<Restaurantes>

    @GET("restaurants/{id}")
    fun getRestauranteById(
        @Path("id") id: Int
    ): Call<Restaurant>
    @POST("api/restaurants")
    fun insertRestaurante(@Header("Authorization") token: String, @Body restaurante: RestaurantInsert): Call<Restaurant>

    // Actualizar información del restaurante
    @PUT("api/restaurants/{id}")
    fun updateRestaurante(@Header("Authorization") token: String, @Path("id") id: Int, @Body restaurante: Restaurant): Call<Restaurant>

    // Eliminar un restaurante
    @DELETE("api/restaurants/{id}")
    fun deleteRestaurante(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>

    // Subir foto de galería para el restaurante
    @Multipart
    @POST("api/restaurants/{id}/photo")
    fun uploadRestaurantePhoto(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part photo: MultipartBody.Part
    ): Call<Photo>

    // Subir logo del restaurante
    @Multipart
    @POST("api/restaurants/{id}/logo")
    fun uploadRestauranteLogo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part logo: MultipartBody.Part
    ): Call<Photo>
    @GET("restaurants/{id}/menu")
    fun getMenuById(
        @Path("id") id: Int
    ): Call<LisMenus>


}