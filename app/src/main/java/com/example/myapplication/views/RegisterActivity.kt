package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.util.ValidationEmailAndPassword
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
        val errorTextView: TextView = findViewById(R.id.error_textView)
        val validation = ValidationEmailAndPassword()
        loginInRegister.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        buttonRegister.setOnClickListener {
            val email = emailRegister.text.toString()
            val password = passwordRegister.text.toString()


            if (validation.emailIsValid(email) && validation.passwordIsValid(password)) {
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
                                    errorTextView.text = it.exception!!.message.toString()
                                    errorTextView.visibility = View.VISIBLE
                                }
                            }
                    } else {
                        errorTextView.text = getString(R.string.password_empty)
                        errorTextView.visibility = View.VISIBLE
                    }
                } else {
                    errorTextView.text = getString(R.string.email_empty)
                    errorTextView.visibility = View.VISIBLE
                }
            } else {
                errorTextView.text = getString(R.string.not_validate)
                errorTextView.visibility = View.VISIBLE

            }
        }
    }
}