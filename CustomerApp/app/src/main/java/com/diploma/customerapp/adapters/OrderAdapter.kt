package com.diploma.customerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.diploma.customerapp.R
import com.diploma.customerapp.model.Order

class OrderAdapter(private val orderList: ArrayList<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)

        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val currentItem = orderList[position]

        holder.userNumber.text = currentItem.userNumber.toString()
        holder.roomNumber.text = currentItem.roomNumber
        holder.orderTime.text = currentItem.time
        holder.orderDetails.text = currentItem.details

        val isVisible : Boolean = currentItem.visibility
        holder.constraintLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

        holder.userNumber.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNumber: TextView = itemView.findViewById(R.id.order_phone_number)
        val roomNumber: TextView = itemView.findViewById(R.id.order_room_number)
        val orderTime: TextView = itemView.findViewById(R.id.food_order_time)
        val orderDetails: TextView = itemView.findViewById(R.id.food_order)

        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.expandedOrderLayout)
    }
}