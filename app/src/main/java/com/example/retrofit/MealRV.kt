package com.example.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.MealRV.UserViewHolder
import com.example.retrofit.databinding.MealListBinding


class MealRV(private val mealList: MutableList<Meal>):RecyclerView.Adapter<UserViewHolder>() {

    inner class UserViewHolder(
        private val binding: MealListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
           val item = mealList[adapterPosition]
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

    override fun getItemCount(): Int = mealList.size


}