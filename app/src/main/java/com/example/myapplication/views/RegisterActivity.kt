package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailRegister: EditText = findViewById(R.id.register_email_edittext)
        val passwordRegister: EditText = findViewById(R.id.register_password_edittext)
        val buttonRegister: Button = findViewById(R.id.register_button)
        val loginInRegister: TextView = findViewById(R.id.register_login_textView)

        loginInRegister.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }

        buttonRegister.setOnClickListener {
            val email = emailRegister.text.toString()
            val password = passwordRegister.text.toString()

            if (email.isNotEmpty() && email.isNotBlank()) {
                if (password.isNotEmpty() && password.isNotBlank()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val firebaseUser: FirebaseUser = it.result!!.user!!


                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("UserId", firebaseUser.uid)
                                intent.putExtra("Email", firebaseUser.email)

                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(
                                    this,
                                    it.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this, "Password should be not empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this, "Email should be not empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}