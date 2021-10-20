package com.example.amigo2

import android.content.Intent
import android.media.AudioMetadata
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class editprofileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)
        // Receiving User id from login screen
        val userid = intent.getStringExtra("userid")


        // getting layout elements in kotlin file
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etmobile = findViewById<EditText>(R.id.etMobnum)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val btnUpdate : Button=findViewById(R.id.btnUpdate)
        val btnBack : Button= findViewById(R.id.btnBack)
        val tvname: TextView=findViewById(R.id.name)


        tvname.text=userid.toString()

        // Getting Reference to the USERS in Realtime Database
        val  database = FirebaseDatabase.getInstance().getReference("USERS")
        if (userid != null) {
            database.child(userid).get().addOnSuccessListener {

                // Setting Text to fields present in data base to change
                etpassword.setText(it.child("password").value.toString())
                etName.setText(it.child("name").value.toString())
                etmobile.setText(it.child("mobile").value.toString())
                etEmail.setText(it.child("email").value.toString())

            }
        }

        // listener when Update button is clicked
        btnUpdate.setOnClickListener {

         // Hash map of updated User details
            val userUpdate = hashMapOf<String,Any>(

            "name" to etName.text.toString(),
            "mobile" to etmobile.text.toString(),
             "email" to etEmail.text.toString(),
             "password" to etpassword.text.toString() ,
            )

            if (userid != null) {
                database.child(userid).updateChildren(userUpdate).addOnSuccessListener {
                    Toast.makeText(this, "Successfully Update", Toast.LENGTH_SHORT).show()

                    // going back to login Screen
                    val intent = Intent(this, MainActivity::class.java).apply {

                    }
                    startActivity(intent)
                }.addOnFailureListener {
                    // if Something went wrong
                    Toast.makeText(this, "Failed try Again", Toast.LENGTH_SHORT).show()
                }
            }

        }

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }


    }
}