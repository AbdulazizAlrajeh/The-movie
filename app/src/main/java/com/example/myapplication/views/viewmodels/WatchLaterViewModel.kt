package com.example.myapplication.views.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Result
import com.example.myapplication.repository.FirebaseServiceRepository
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "WatchLaterViewModel"

class WatchLaterViewModel : ViewModel() {

    private val firebaseRepository = FirebaseServiceRepository.get()

    val watchLaterLiveData = MutableLiveData<List<Result>>()
    val watchLaterMoviesLiveDataException = MutableLiveData<String>()
    fun getMoviesWatchLater(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseRepository.getMoviesWatchLater(true, userId).get()

                response.addOnSuccessListener {
                    if (response.isSuccessful) {
                        val list = mutableListOf<Result>()
                        for (document in it.documents) {
                            val movie = document.toObject<Result>()
                            list.add(movie!!)
                        }

                        watchLaterLiveData.postValue(list)

                    } else {
                        Log.d(TAG, response.exception!!.message.toString())

                        watchLaterMoviesLiveDataException
                            .postValue(response.exception!!.message.toString())

                    }
                }


            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                // Send Error Response to view
                watchLaterMoviesLiveDataException.postValue(e.message.toString())
            }

        }

    }


    fun updateItem(result: Result, newResult: Map<String, Any>) {
        viewModelScope.launch(Dispatchers.IO) {

            val response = firebaseRepository.updateWatcherLater(result, newResult).get()
            response.addOnSuccessListener {
                if (response.result!!.documents.isNotEmpty()) {
                    for (document in it) {
                        try {
                            firebaseRepository.personalCollection.document(document.id).set(
                                newResult, SetOptions.merge()
                            )
                        } catch (e: Exception) {
                            watchLaterMoviesLiveDataException.postValue(e.message.toString())

                        }
                    }
                } else {
                    watchLaterMoviesLiveDataException.postValue(response.exception?.message.toString())
                }

            }
        }
    }


    fun deleteItem(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = firebaseRepository.deleteWatchLater(result).get()
            response.addOnSuccessListener {
                for (document in response.result!!) {
                    try {
                        firebaseRepository.personalCollection.document(document.id).delete()
                    } catch (e: java.lang.Exception) {
                        watchLaterMoviesLiveDataException.postValue("Exception in delete")
                    }

                }
            }
        }
    }
}


