package com.example.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.retrofit.RestaurantRV.UserViewHolder
import com.example.retrofit.databinding.MealListBinding


class RestaurantRV(private val restaurantList: MutableList<Restaurant>):RecyclerView.Adapter<UserViewHolder>() {

    inner class UserViewHolder(
        private val binding: MealListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
           val item = restaurantList[adapterPosition]
            binding.apply {
                mealName.text = item.name
                mealPrice.text = "Price: $"+item.price
                mealNuts.text = "Nuts: "+item.nuts
                mealVegetarian.text = "Vegetarian: "+item.vegetarian

                Glide.with(itemView.context)
                    .load(item.image)
                    .placeholder(R.drawable.loading_image)
                    .into(mealImage)

                mealSpiciness.rating = item.spiciness.toFloat()

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       return UserViewHolder(MealListBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       ))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()

    }

    override fun getItemCount(): Int = restaurantList.size


}