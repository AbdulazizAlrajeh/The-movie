package com.example.myapplication.views.logInActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.views.MainActivity
import com.example.myapplication.views.registerActivity.RegisterActivity
import com.example.myapplication.views.registerActivity.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth


private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
var SHARED_PREF_FILE = "saved_status"

class LogInActivity : AppCompatActivity() {
    private val loginViewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        observers()

        val emailLogIn: EditText = findViewById(R.id.login_email_edittext)
        val passwordLogIn: EditText = findViewById(R.id.login_password_edittext)
        val buttonLogIn: Button = findViewById(R.id.login_button)
        val registerInLogin: TextView = findViewById(R.id.login_register_textView)
        val errorTextView:TextView = findViewById(R.id.error_textView)


        registerInLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        buttonLogIn.setOnClickListener {
            val email = emailLogIn.text.toString()
            val password = passwordLogIn.text.toString()

            if (email.isNotEmpty() && email.isNotBlank()) {
                if (password.isNotEmpty() && password.isNotBlank()) {
                   loginViewModel.callLogIn(email, password)

                } else {

                    errorTextView.text = getString(R.string.password_empty)
                    errorTextView.visibility = View.VISIBLE
                }
            } else {
                errorTextView.text = getString(R.string.email_empty)
                errorTextView.visibility = View.VISIBLE

            }
        }
    }

    fun observers(){
        loginViewModel.firebaseAuthCorrectLiveData.observe(this, Observer {
            it?.let {
                Log.d("True", it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putBoolean("state login", true)
                sharedPrefEditor.commit()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        loginViewModel.firebaseAuthExceptionLiveData.observe(this, Observer {
            Log.d("Error", it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            loginViewModel.firebaseAuthExceptionLiveData.postValue(null)
        })
    }
}