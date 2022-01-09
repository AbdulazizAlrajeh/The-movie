package com.example.myapplication.views.registerActivity

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Users
import com.example.myapplication.repository.FirebaseAuthServiceRepository
import com.example.myapplication.repository.FirebaseServiceRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel:ViewModel() {
    val firebaseStore = FirebaseServiceRepository
    val firebaseAuth = FirebaseAuthServiceRepository
    val firebaseAuthCorrectLiveData = MutableLiveData<String>()
    val firebaseAuthExceptionLiveData = MutableLiveData<String>()

    fun callSaveUser(email: String, password: String,firstName:String,lastName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseAuth.createUser(email, password)
                response.addOnSuccessListener {
                        val user = Users(firstName, lastName, email,FirebaseAuth.getInstance().uid.toString())
                        firebaseStore.saveUserInfo(user)
                        firebaseAuthCorrectLiveData.postValue("Save user to database")
                }
            }catch (e:Exception){
                firebaseAuthExceptionLiveData.postValue(e.message.toString())
            }
        }
    }

}