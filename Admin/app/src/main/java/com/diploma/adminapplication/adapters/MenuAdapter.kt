package com.diploma.adminapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diploma.adminapplication.R
import com.diploma.adminapplication.model.Menu

class MenuAdapter(private val menuList : ArrayList<Menu>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]

        holder.foodName.text = currentItem.foodName
        holder.foodPrice.text = currentItem.foodPrice
    }

    override fun getItemCount(): Int {
        return menuList.size
    }


    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName : TextView = itemView.findViewById(R.id.food_name)
        val foodPrice : TextView = itemView.findViewById(R.id.food_price)
    }
}





