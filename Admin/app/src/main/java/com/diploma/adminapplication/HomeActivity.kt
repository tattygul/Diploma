package com.diploma.adminapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var logout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        logout = findViewById(R.id.logout)

        logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
            finish()
        }

    }
}