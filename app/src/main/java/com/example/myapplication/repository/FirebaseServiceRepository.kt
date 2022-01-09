package com.example.myapplication.repository

import android.content.Context
import android.net.Uri
import com.example.myapplication.models.Result
import com.example.myapplication.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception

object FirebaseServiceRepository {

    private var storageReference = FirebaseStorage.getInstance().getReference()
    val personalCollection = Firebase.firestore.collection("watch")

    val usersCollection = Firebase.firestore.collection("users")

    fun saveUserInfo(user: Users) =
        usersCollection.document(FirebaseAuth.getInstance().uid.toString())
            .set(user)

    fun getUserInfo() = usersCollection.document(FirebaseAuth.getInstance().uid.toString())
        .get()

    fun uploadImageUser(imageUri: Uri) =
        storageReference.child(FirebaseAuth.getInstance().uid.toString()).putFile(imageUri)

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