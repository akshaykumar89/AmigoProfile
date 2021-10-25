package com.example.amigo2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class Verify_OTP : AppCompatActivity() {
    lateinit var number: String
    var otpID: String = ""
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify__o_t_p)
        val tvNUm: TextView = findViewById(R.id.number)
        val etOTP: EditText = findViewById(R.id.etOTP)
        val btnVerify: Button = findViewById(R.id.btnVrify)
        auth = FirebaseAuth.getInstance()
        number = intent.getStringExtra("mobilnumber").toString()
        tvNUm.text = number
        initiateOPT()
        btnVerify.setOnClickListener {
            if (etOTP.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Blank otp field", Toast.LENGTH_SHORT).show()
            } else if (etOTP.text.toString().length != 6) {
                Toast.makeText(applicationContext, "Invalid OTP", Toast.LENGTH_SHORT).show()
            } else {
                var credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(otpID, etOTP.text.toString())

                signInWithPhoneAuthCredential(credential)
            }
        }


    }


    private fun initiateOPT() {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {

                    otpID = verificationId
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, Registration_complete::class.java).apply {

                    }
                    startActivity(intent)
                    finish()



                } else {
                    Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show()
                }
            }
    }


}