package com.example.amigo2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)



        // refrence to USERS in data base
        val  database = FirebaseDatabase.getInstance().getReference("USERS")
        val etUserid = findViewById<EditText>(R.id.etUserID)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin : Button = findViewById(R.id.btnUpdate)
        val btnRegis : Button = findViewById(R.id.btnReg)
        btnLogin.setOnClickListener {
            if(etUserid.text.toString()=="" && etpassword.text.toString()=="")
            {
                Toast.makeText(this, "Enter User ID and Password", Toast.LENGTH_SHORT).show()
            }
            else if(etUserid.text.toString()=="")
            {
                Toast.makeText(this, "Enter User ID", Toast.LENGTH_SHORT).show()
            }
            else if(etpassword.text.toString()=="")
            {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            }
            else
            {
                database.child(etUserid.text.toString()).get().addOnSuccessListener {
                    if(it.exists())
                    {
                        if(it.child("password").value.toString()==etpassword.text.toString())
                        {
                            Toast.makeText(this, "Login Sucessful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, editprofileActivity::class.java).apply {
                                putExtra("userid",etUserid.text.toString())
                            }
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "User not Registered \n please Register", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed.. Try again", Toast.LENGTH_SHORT).show()
                }
            }

        }
        btnRegis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {  }
            startActivity(intent)
        }




    }
}

