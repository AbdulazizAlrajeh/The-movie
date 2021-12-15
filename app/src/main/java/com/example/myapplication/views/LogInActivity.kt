package com.example.myapplication.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth


private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
var SHARED_PREF_FILE = "saved_status"

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        val emailLogIn: EditText = findViewById(R.id.login_email_edittext)
        val passwordLogIn: EditText = findViewById(R.id.login_password_edittext)
        val buttonLogIn: Button = findViewById(R.id.login_button)
        val registerInLogin: TextView = findViewById(R.id.login_register_textView)

        registerInLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        buttonLogIn.setOnClickListener {
            val email = emailLogIn.text.toString()
            val password = passwordLogIn.text.toString()

            if (email.isNotEmpty() && email.isNotBlank()) {
                if (password.isNotEmpty() && password.isNotBlank()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)

                                intent.putExtra(
                                    "UserId",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra(
                                    "Email",
                                    FirebaseAuth.getInstance().currentUser!!.email
                                )

                                sharedPref = this.getSharedPreferences(
                                    SHARED_PREF_FILE,
                                    Context.MODE_PRIVATE
                                )
                                sharedPrefEditor = sharedPref.edit()
                                sharedPrefEditor.putBoolean("state login", true)
                                sharedPrefEditor.commit()

                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this, it.exception!!.message.toString(),
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