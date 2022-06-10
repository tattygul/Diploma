package com.diploma.customerapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diploma.customerapp.adapters.RoomAdapter
import com.diploma.customerapp.model.Room
import com.google.firebase.database.*
import java.util.ArrayList

class RoomFragment : Fragment() {


    private lateinit var databaseReference: DatabaseReference
    private lateinit var roomRecyclerView: RecyclerView
    private lateinit var roomArrayList: ArrayList<Room>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomRecyclerView = view.findViewById(R.id.room_recycler_view)
        LinearLayoutManager(activity).reverseLayout = true
        LinearLayoutManager(activity).stackFromEnd = false

        roomRecyclerView.layoutManager = LinearLayoutManager(activity)
        roomRecyclerView.setHasFixedSize(true)

        roomArrayList = arrayListOf<Room>()
        getRoomData()
    }


    private fun getRoomData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Room")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomArrayList.clear()
                if (snapshot.exists()) {
                    for (roomSnapshot in snapshot.children) {
                        val room = roomSnapshot.getValue(Room::class.java)
                        roomArrayList.add(room!!)
                    }

                    val adapter = RoomAdapter(context!!, roomArrayList)
                    roomRecyclerView.adapter = adapter



                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }


}