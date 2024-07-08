package com.example.reservatron.api


import com.example.reservatron.model.login.LisMenus
import com.example.reservatron.model.login.Photo
import com.example.reservatron.model.login.ReservaList
import com.example.reservatron.model.login.ReservacionRequestDTO
import com.example.reservatron.model.login.ReservacionResponseDTO
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.RestaurantInsert
import com.example.reservatron.model.login.RestauranteFiltroDTO
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
    @POST("restaurants")
    fun insertRestaurante(
        @Body restaurant: RestaurantInsert
    ): Call<Void>

    @GET("restaurants")
    fun getRestauranterByUsuario(): Call<Restaurantes>

    @DELETE("restaurants/{id}")
    fun deleteRestaurante(
        @Path("id") id: Int
    ): Call<Void>


    @GET("restaurants/{id}/menu")
    fun getMenuById(
        @Path("id") id: Int
    ): Call<LisMenus>
    @PUT("restaurants/{id}")
    fun editRestaurante(
        @Path("id") id: Int,
        @Body restaurante: RestaurantInsert
    ): Call<Void>


    @Multipart
    @POST("restaurants/{id}/logo")
    fun uploadLogo(
        @Path("id") id: Int,
        @Part logo: MultipartBody.Part
    ): Call<Void>

    @Multipart
    @POST("restaurants/{id}/photo")
    fun uploadGallery(
        @Path("id") id: Int,
        @Part gallery: MultipartBody.Part
    ): Call<Void>

    @POST("restaurants/search")
    fun getRestaurantesFiltered(
        @Body filter: RestauranteFiltroDTO
    ): Call<Restaurantes>

    @POST("restaurants")
    fun createRestaurant(
        @Body restaurant: RestaurantInsert
    ): Call<Void>

    @POST("reservations")
    fun makeReservation(
        @Body reservation: ReservacionRequestDTO
    ): Call<ReservacionResponseDTO>

    @GET("reservations")
    fun getReservas(): Call<ReservaList>

    @POST("reservations/{id}/cancel")
    fun cancelReservation(
        @Path("id") id: Long
    ): Call<Void>

    @GET("restaurants/{id}/reservations")
    fun getReservasByRestaurante(
        @Path("id") id: Int
    ): Call<ReservaList>

}