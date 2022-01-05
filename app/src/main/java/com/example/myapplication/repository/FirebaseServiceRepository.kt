package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.models.Result
import com.example.myapplication.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

object FirebaseServiceRepository {
    val personalCollection = Firebase.firestore.collection("watch")

    val usersCollection = Firebase
        .firestore.collection("users").document("${FirebaseAuth.getInstance().currentUser?.uid}")

    fun saveUserInfo(user:Users) = usersCollection.set(user)
    fun getUserInfo() = usersCollection.get()


     fun saveMovie(result: Result) =
        personalCollection.add(result)

     fun getMoviesWatchLater(watchLater: Boolean, userId: String) =
        personalCollection

            .whereEqualTo("iswatchedLater", watchLater)
            .whereEqualTo("userId", userId)

     fun getMoviesWatched(watched: Boolean, userId: String) =
        personalCollection

            .whereEqualTo("watched", watched)
            .whereEqualTo("userId", userId)

     fun updateWatcherLater(result: Result, newResult: Map<String, Any>) =
        personalCollection
            .whereEqualTo("id", result.id)


     fun deleteWatchLater(result: Result) =
        personalCollection
            .whereEqualTo("id", result.id)



}