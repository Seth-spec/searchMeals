package com.example.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MealModel
    private val viewModelFactory = RestaurantViewModelFactory(RetrofitClient.retrofit)
    private val mealList = mutableListOf<Meal>()
    private val adapter = MealRV(mealList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MealModel::class.java)

        lifecycleScope.launch {

            viewModel.getData().collect {
                mealList.clear()
                mealList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        binding.apply {
            recyclerView.adapter = adapter
           recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)

            searchButton.setOnClickListener {
                val searchText = searchView.query.toString()
                search(searchText)
            }
        }


    }

    private fun search(query: String) {
        lifecycleScope.launch {
            val result = if (query.isNotEmpty()) {
                searchMeals(query)
            } else {
                viewModel.getData()
            }
            result.collect {
                mealList.clear()
                mealList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun searchMeals(query: String): Flow<List<Meal>> = flow {
        val filteredList = if (query.isNotEmpty()) {
            mealList.filter { restaurant ->
                restaurant.name.contains(query, ignoreCase = true)
            }
        } else {
            mealList
        }
        emit(filteredList)
    }
}



class RestaurantViewModelFactory(
    private val api: MealApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealModel(api) as T
        }
        throw IllegalArgumentException("Unknown VieModel class")
    }
}