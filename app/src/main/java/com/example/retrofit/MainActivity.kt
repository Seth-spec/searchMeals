package com.example.retrofit

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: RestaurantViewModel
    private val viewModelFactory = RestaurantViewModelFactory(RetrofitClient.retrofit)
    private val restaurantList = mutableListOf<Restaurant>()
    private val adapter = RestaurantRV(restaurantList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)

        lifecycleScope.launch {

            viewModel.getData().collect {
                restaurantList.clear()
                restaurantList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            searchButton.setOnClickListener {
                val searchText = searchView.query.toString()
                searchRestaurants(searchText)
            }
        }


    }

    private fun searchRestaurants(query: String) {
        lifecycleScope.launch {
            val result = if (query.isNotEmpty()) {
                searchMeals(query)
            } else {
                viewModel.getData()
            }
            result.collect {
                restaurantList.clear()
                restaurantList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun searchMeals(query: String): Flow<List<Restaurant>> = flow {
        val filteredList = if (query.isNotEmpty()) {
            restaurantList.filter { restaurant ->
                restaurant.name.contains(query, ignoreCase = true)
            }
        } else {
            restaurantList
        }
        emit(filteredList)
    }
}



class RestaurantViewModelFactory(
    private val api: RestaurantApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown VieModel class")
    }
}