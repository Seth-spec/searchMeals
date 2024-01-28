package com.example.retrofit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MealModel(
    private val api: MealApi
) : ViewModel() {

    fun getData(): Flow<List<Meal>> = flow {
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