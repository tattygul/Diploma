package com.diploma.customerapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diploma.customerapp.R
import com.diploma.customerapp.model.Menu

class MenuAdapter(private val context: Context, private val menuList: ArrayList<Menu>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]

        Glide.with(context)
            .load(currentItem.menuImageUrl)
            .into(holder.foodImage)
        holder.foodCategory.text = currentItem.foodCategory
        holder.foodName.text = currentItem.foodName
        holder.foodPrice.text = currentItem.foodPrice.toString()
        holder.setIsRecyclable(false)

    }

    override fun getItemCount(): Int {
        return menuList.size
    }


    class MenuViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.food_image)
        val foodCategory: TextView = itemView.findViewById(R.id.food_category)
        val foodName: TextView = itemView.findViewById(R.id.food_name)
        val foodPrice: TextView = itemView.findViewById(R.id.food_price)
    }
}





