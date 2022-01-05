package com.example.myapplication.views.registerActivity

import android.content.Intent
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
import com.example.myapplication.util.ValidationEmailAndPassword
import com.example.myapplication.views.logInActivity.LogInActivity


class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        observers()

        val emailRegister: EditText = findViewById(R.id.register_email_edittext)
        val passwordRegister: EditText = findViewById(R.id.register_password_edittext)
        val buttonRegister: Button = findViewById(R.id.register_button)
        val loginInRegister: TextView = findViewById(R.id.register_login_textView)
        val firstNameEditText:EditText = findViewById(R.id.firstname_editText)
        val lastNameEditText:EditText = findViewById(R.id.lastname_editText)
        val errorTextView: TextView = findViewById(R.id.error_textView)
        val validation = ValidationEmailAndPassword()
        loginInRegister.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        buttonRegister.setOnClickListener {
            val email = emailRegister.text.toString()
            val password = passwordRegister.text.toString()
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()

            if (validation.emailIsValid(email) && validation.passwordIsValid(password)) {
                if (email.isNotEmpty() && email.isNotBlank()) {
                    if (password.isNotEmpty() && password.isNotBlank()) {
                        registerViewModel.callSaveUser(email,password,firstName,lastName)

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

    fun observers(){
        registerViewModel.firebaseAuthCorrectLiveData.observe(this, Observer {
            it?.let {
                Log.d("True", it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }
        })

        registerViewModel.firebaseAuthExceptionLiveData.observe(this, Observer {
            Log.d("Error", it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            registerViewModel.firebaseAuthExceptionLiveData.postValue(null)
        })
    }
}