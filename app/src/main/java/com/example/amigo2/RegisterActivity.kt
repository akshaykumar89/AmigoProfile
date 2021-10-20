package com.example.amigo2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
      lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)

        // Getting Reference to the USERS in Realtime Database
          database = FirebaseDatabase.getInstance().getReference("USERS")

       // getting layout elements in kotlin file
        val etUserid = findViewById<EditText>(R.id.etUserID)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etmobile = findViewById<EditText>(R.id.etMobnum)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister : Button = findViewById(R.id.btnUpdate)
        val btnBack : Button= findViewById(R.id.btnBack)

        //When Register Button is clicked---
        btnRegister.setOnClickListener {
            // chekIf function check if All fields are filled and User ID is not present already

           if(  checkIf(etUserid.text.toString(),
            etName.text.toString(),
            etmobile.text.toString(),
            etEmail.text.toString(),
            etpassword.text.toString() ))

           {
               // Getting  New User data and storing it in 'newuser' of 'User' data class type
               val newuser = User(etUserid.text.toString(),
                       etName.text.toString(),
                       etmobile.text.toString(),
                       etEmail.text.toString(),
                       etpassword.text.toString())

               // Writing data in Firebase Realtime database
               database.child(etUserid.text.toString()).setValue(newuser).addOnSuccessListener {

                   // Listener when data is stored successfully

                   Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                   val intent = Intent(this, MainActivity::class.java).apply {
                   }
                   startActivity(intent)

               }.addOnFailureListener{
                   // Listener when data does not store successfully
                   Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


               }
           }




        }
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }



    }

    private fun checkIf(userid: String, name: String, mobile: String, email: String, password: String): Boolean {

        if(userid=="" || name=="" || mobile=="" || email==""|| password=="" )
        {
            Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_SHORT).show()
            return false
        }
        else
        {
            var check : Boolean = true
            database.get().addOnSuccessListener {
                if(it.exists())

                    Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show()
                     check=false

            }
            return check
        }
        return true

    }


}