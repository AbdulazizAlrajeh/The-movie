package com.example.myapplication.repository

import android.content.Context
import android.widget.Toast
import com.example.myapplication.models.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FirebaseServiceRepository (context: Context){
    private val personalCollection = Firebase.firestore

    suspend fun saveMovie(result: Result) = personalCollection
        .collection("${ FirebaseAuth.getInstance().uid }").add(result)




    companion object {
        private var instance: FirebaseServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = FirebaseServiceRepository(context)
        }

        fun get() : FirebaseServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }
}