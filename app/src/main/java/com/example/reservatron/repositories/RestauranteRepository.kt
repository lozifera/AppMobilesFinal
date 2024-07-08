package com.example.reservatron.repositories

import android.graphics.Bitmap
import android.util.Log
import com.example.reservatron.api.APIrestaurantes
import com.example.reservatron.model.login.LisMenus
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.RestaurantInsert
import com.example.reservatron.model.login.Restaurantes
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response
import java.io.ByteArrayOutputStream
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
        restaurant: RestaurantInsert,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)

        service.insertRestaurante(restaurant).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }
    fun getRestaurantesByUsuario(
        token: String,
        success: (Restaurantes?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)

        service.getRestauranterByUsuario().enqueue(object : Callback<Restaurantes> {
            override fun onResponse(call: Call<Restaurantes>, response: Response<Restaurantes>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurantes>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurante(
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)

        service.deleteRestaurante(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }
    fun getMenuById(id: Int, success: (LisMenus?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIrestaurantes =
            retrofit.create(APIrestaurantes::class.java)

        service.getMenuById(id).enqueue(object : Callback<LisMenus> {
            override fun onResponse(call: Call<LisMenus>, response: Response<LisMenus>) {
                success(response.body())
            }

            override fun onFailure(call: Call<LisMenus>, t: Throwable) {
                failure(t)
            }
        })
    }
    fun editRestaurant(
        restaurant: RestaurantInsert,
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)

        service.editRestaurante(id, restaurant).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun uploadLogo(
        id: Int,
        bitmap: Bitmap,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        // Convert Bitmap to ByteArray
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()


        // Create RequestBody and MultipartBody.Part
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), bitmapData)
        val body = MultipartBody.Part.createFormData("image", "logo.jpg", requestFile)

        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)
        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)

        service.uploadLogo(id, body).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                    Log.d("Response", response.body().toString())
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun uploadGallery(
        id: Int,
        bitmap: Bitmap,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        // Convert Bitmap to ByteArray
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()
        // Create RequestBody and MultipartBody.Part
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), bitmapData)
        val body = MultipartBody.Part.createFormData("image", "gallery.jpg", requestFile)
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)
        val service: APIrestaurantes = retrofit.create(APIrestaurantes::class.java)
        service.uploadGallery(id, body).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                    Log.d("Response", response.body().toString())
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })


    }


}




