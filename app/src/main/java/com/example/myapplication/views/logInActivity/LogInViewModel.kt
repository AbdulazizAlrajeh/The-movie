package com.example.myapplication.views.logInActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Users
import com.example.myapplication.repository.FirebaseAuthServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInViewModel:ViewModel() {

    val firebaseAuth = FirebaseAuthServiceRepository
    val firebaseAuthCorrectLiveData = MutableLiveData<String>()
    val firebaseAuthExceptionLiveData = MutableLiveData<String>()

    fun callLogIn(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseAuth.signInUser(email, password)
                response.addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseAuthCorrectLiveData.postValue("LogIn")

                    } else {
                        firebaseAuthExceptionLiveData.postValue("Not LogIn")
                    }
                }
            }catch (e:Exception){
                firebaseAuthExceptionLiveData.postValue(e.message.toString())
            }
        }

    }
}