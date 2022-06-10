package com.diploma.customerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diploma.customerapp.adapters.MenuAdapter
import com.diploma.customerapp.model.Menu
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MenuFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuArrayList: ArrayList<Menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuRecyclerView = view.findViewById(R.id.menu_recycler_view)
        LinearLayoutManager(activity).reverseLayout = true
        LinearLayoutManager(activity).stackFromEnd = true

        menuRecyclerView.layoutManager = LinearLayoutManager(activity)
        menuRecyclerView.setHasFixedSize(true)

        menuArrayList = arrayListOf<Menu>()
        getMenuData()
    }

    private fun getMenuData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Menu")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuArrayList.clear()
                if (snapshot.exists()) {
                    for (menuSnapshot in snapshot.children) {
                        val menu = menuSnapshot.getValue(Menu::class.java)
                        menuArrayList.add(menu!!)
                    }

                    val adapter = MenuAdapter(context!!, menuArrayList)
                    menuRecyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

}