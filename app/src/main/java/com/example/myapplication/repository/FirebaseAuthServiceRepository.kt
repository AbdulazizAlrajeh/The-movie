package com.example.myapplication.repository


import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthServiceRepository {

    val AuthServiceRepository = FirebaseAuth.getInstance()

    fun createUser(email: String, password: String) =
        AuthServiceRepository.createUserWithEmailAndPassword(email, password)

    fun signInUser(email: String, password: String) =
        AuthServiceRepository.signInWithEmailAndPassword(email, password)
}