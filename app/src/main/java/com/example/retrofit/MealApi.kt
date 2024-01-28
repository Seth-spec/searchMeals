package com.example.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface MealApi {

    @GET("api/Products/GetAll")
    suspend fun getAllProducts(): Response<List<Meal>>

}