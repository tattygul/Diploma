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
import com.diploma.customerapp.model.Room

class RoomAdapter(private val context: Context, private val roomList: ArrayList<Room>) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
        return RoomAdapter.RoomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val currentItem = roomList[position]

        Glide.with(context)
            .load(currentItem.roomImageUri)
            .into(holder.roomImage)

        holder.roomNumber.text = currentItem.roomNumber
        holder.roomCapacity.text = currentItem.roomCapacity.toString()
        holder.roomPrice.text = currentItem.roomPrice.toString()
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class RoomViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val roomImage: ImageView = itemView.findViewById(R.id.room_image)
        val roomNumber: TextView = itemView.findViewById(R.id.room_number)
        val roomCapacity: TextView = itemView.findViewById(R.id.room_capacity)
        val roomPrice: TextView = itemView.findViewById(R.id.room_price)

    }
}