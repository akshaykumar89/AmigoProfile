package com.example.amigo2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration_complete : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_complete)

        val tvname:TextView=findViewById(R.id.tvName)
        val tvuserid:TextView=findViewById(R.id.tvUserid)
        val tvmobile:TextView=findViewById(R.id.tvMobile)
        val tvemail:TextView=findViewById(R.id.tvEmail)
        val btnLogin : Button = findViewById(R.id.btnLogin)
        val tvStatus : TextView= findViewById(R.id.tvStatus)


        val sRef: SharedPreferences = getSharedPreferences("key", MODE_PRIVATE)
        val name: String? = sRef.getString("name", "")
        val userid: String? = sRef.getString("userid", "")
        val mobile: String? = sRef.getString("mobilenumber", "")
        val email: String? = sRef.getString("email", "")
        val password: String? = sRef.getString("password", "")

        tvname.text=name
        tvuserid.text=userid
        tvmobile.text=mobile
        tvemail.text=email

// Getting Reference to the USERS in Realtime Database
        database = FirebaseDatabase.getInstance().getReference("USERS")

        // Getting  New User data and storing it in 'newuser' of 'User' data class type
                            val newuser = User(userid,name,mobile,email,password)

        // Writing data in Firebase Realtime database
        if (userid != null) {
            database.child(userid).setValue(newuser).addOnSuccessListener {

                // Listener when data is stored successfully

                tvStatus.text="Registered"
                btnLogin.isEnabled=true


            }.addOnFailureListener {
                // Listener when data does not store successfully
               tvStatus.text="Failed"


            }
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {

            }
            startActivity(intent)
            finish()
        }




    }
}



