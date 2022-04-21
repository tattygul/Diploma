package com.diploma.adminapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var phoneEditText: EditText
    lateinit var otpEditText: EditText
    lateinit var sentButton: Button
    lateinit var verifyButton: Button
    lateinit var auth: FirebaseAuth
    lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneEditText = findViewById(R.id.phone_edit_text)
        otpEditText = findViewById(R.id.OTP_edit_text)
        sentButton = findViewById(R.id.sent_button)
        verifyButton = findViewById(R.id.verify_button)

        auth = FirebaseAuth.getInstance()

        sentButton.setOnClickListener {
            if (TextUtils.isEmpty(phoneEditText.text.toString())) {
                Toast.makeText(
                    applicationContext,
                    "Enter a valid phone number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var number: String = phoneEditText.text.toString()
                sentVerification(number)
            }
        }

        verifyButton.setOnClickListener {
            if (TextUtils.isEmpty(otpEditText.text.toString())) {
                Toast.makeText(applicationContext, "Wrong OTP Entered", Toast.LENGTH_SHORT)
                    .show()
            } else {
                verifyCode(otpEditText.text.toString())
            }
        }
    }

    private fun sentVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+996$phoneNumber")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(@NonNull credential: PhoneAuthCredential) {
            val code: String? = credential.smsCode
            if (code != null) {
                verifyCode(code)
            }

        }

        override fun onVerificationFailed(@NonNull e: FirebaseException) {
            Toast.makeText(applicationContext, "Verification Failed", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            s: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(s, token)
            verificationId = s
        }
    }

    private fun verifyCode(code: String) {
        var phoneAuthCredential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(verificationId, code)
        signinByCredential(phoneAuthCredential)
    }

    private fun signinByCredential(phoneAuthCredential: PhoneAuthCredential) {
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                }
            }
    }

    override fun onStart() {
        super.onStart()

        var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }
    }

}

