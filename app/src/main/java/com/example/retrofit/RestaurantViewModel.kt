package com.example.retrofit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestaurantViewModel(
    private val api: RestaurantApi
) : ViewModel() {

    fun getData(): Flow<List<Restaurant>> = flow {
        try {
            val response = api.getAllProducts()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }


}