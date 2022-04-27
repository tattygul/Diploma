package com.diploma.adminapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diploma.adminapplication.model.Menu
import com.diploma.adminapplication.adapters.MenuAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class MenuFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuArrayList: ArrayList<com.diploma.adminapplication.model.Menu>

    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        floatingActionButton = view.findViewById(R.id.add_new_food)
        floatingActionButton.setOnClickListener {
            addFood()
        }
    }

    private fun addFood() {
        var dialog : AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater : LayoutInflater = LayoutInflater.from(activity)

        var view : View = inflater.inflate(R.layout.input_file, null)
        dialog.setView(view)

        var myDialog = dialog.create()
        myDialog.setCancelable(false)
        myDialog.show()
    }


    private fun getMenuData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Menu")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (menuSnapshot in snapshot.children) {
                        val menu = menuSnapshot.getValue(Menu::class.java)
                        menuArrayList.add(menu!!)
                    }

                    menuRecyclerView.adapter = MenuAdapter(menuArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}