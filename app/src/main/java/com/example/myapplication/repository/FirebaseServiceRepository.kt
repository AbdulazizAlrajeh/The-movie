package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.models.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class FirebaseServiceRepository(context: Context) {
    val personalCollection = Firebase.firestore.collection("watch")
    suspend fun saveMovie(result: Result) =
        personalCollection.add(result)

    suspend fun getMovies(result: Result) =
        personalCollection
            .whereEqualTo("id", result.id)
            .whereEqualTo("watched", result.isWatched)

    suspend fun getMoviesWatchLater(watchLater: Boolean, userId: String) =
        personalCollection

            .whereEqualTo("iswatchedLater", watchLater)
            .whereEqualTo("userId", userId)

    suspend fun getMoviesWatched(watched: Boolean, userId: String) =
        personalCollection

            .whereEqualTo("watched", watched)
            .whereEqualTo("userId", userId)

    suspend fun updateWatcherLater(result: Result, newResult: Map<String, Any>) =
        personalCollection
            .whereEqualTo("id", result.id)


    suspend fun deleteWatchLater(result: Result) =
        personalCollection
            .whereEqualTo("id", result.id)


    companion object {
        private var instance: FirebaseServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = FirebaseServiceRepository(context)
        }

        fun get(): FirebaseServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }
}