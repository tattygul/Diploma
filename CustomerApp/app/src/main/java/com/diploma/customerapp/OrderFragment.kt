package com.diploma.customerapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diploma.customerapp.adapters.OrderAdapter
import com.diploma.customerapp.model.Order
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrderFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderArrayList: ArrayList<Order>
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var loader: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderRecyclerView = view.findViewById(R.id.order_recycler_view)
        LinearLayoutManager(activity).reverseLayout = true
        LinearLayoutManager(activity).stackFromEnd = true

        orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        orderRecyclerView.setHasFixedSize(true)

        orderArrayList = arrayListOf<Order>()
        getOrderData()

        firebaseAuth = FirebaseAuth.getInstance()

        loader = ProgressDialog(activity)

        floatingActionButton = view.findViewById(R.id.add_new_order)
        floatingActionButton.setOnClickListener {
            addOrder()
        }

    }


    private fun addOrder() {
        var dialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = LayoutInflater.from(activity)

        var view: View = inflater.inflate(R.layout.input_order, null)
        dialog.setView(view)

        var myDialog = dialog.create()
        myDialog.setCancelable(false)
        myDialog.show()

        var spinner: Spinner = view.findViewById(R.id.room_spinner)
        var timeEditText: EditText =
            view.findViewById(R.id.new_order_time)
        var detailsEditText: EditText =
            view.findViewById(R.id.details_edit_text)

        val arrayAdapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.order_spinner,
                android.R.layout.simple_spinner_item
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
//        spinner.setOnItemClickListener(context)

        var save: Button = view.findViewById(R.id.save_button)
        var cancel: Button = view.findViewById(R.id.cancel_button)


        cancel.setOnClickListener { myDialog.dismiss() }

        save.setOnClickListener {
            val id: String = databaseReference.push().key!!
            val phoneNumber = firebaseAuth.currentUser?.phoneNumber
            val roomNumber: String = spinner.selectedItem.toString()
            val time: String = timeEditText.text.toString()
            val details: String = detailsEditText.text.toString()


            if (TextUtils.isEmpty(details)) {
                detailsEditText.error = "Name of the Food is required!"

            } else {
                loader.setMessage("Adding order")
                loader.setCanceledOnTouchOutside(false)
                loader.show()

                var order = Order(id, phoneNumber, roomNumber, time, details)

                databaseReference.child(id).setValue(order).addOnCompleteListener {
                    if (it.isSuccessful) {
                        loader.dismiss()
                        Toast.makeText(activity, "Order has successfully added", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            myDialog.dismiss()
        }
        myDialog.show()

    }


    private fun getOrderData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Order")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderArrayList.clear()
                if (snapshot.exists()) {
                    for (orderSnapshot in snapshot.children) {
                        val order = orderSnapshot.getValue(Order::class.java)
                        orderArrayList.add(order!!)
                    }

                    val adapter = OrderAdapter(orderArrayList)
                    orderRecyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}