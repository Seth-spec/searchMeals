package com.example.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RestaurantApi {

    @GET("api/Products/GetAll")
    suspend fun getAllProducts(): Response<List<Restaurant>>

}