package com.example.myapplication.views.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Result
import com.example.myapplication.repository.FirebaseServiceRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "WatchedViewModel"

class WatchedViewModel : ViewModel() {

    private val firebaseRepository = FirebaseServiceRepository
    val watchedLiveData = MutableLiveData<List<Result>>()
    val watchedMoviesLiveDataException = MutableLiveData<String>()
    var selectedItemMutableLiveData = MutableLiveData<Result>()

    fun callWatchedMovies(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseRepository.getMoviesWatched(true, userId).get()
                response.addOnSuccessListener {
                    if (response.isSuccessful) {
                        val list = mutableListOf<Result>()
                        for (document in it.documents) {
                            val movie = document.toObject<Result>()
                            list.add(movie!!)
                        }
                        watchedLiveData.postValue(list)
                    } else {

                        watchedMoviesLiveDataException.postValue(response.exception.toString())

                    }
                }
            } catch (e: Exception) {
                watchedMoviesLiveDataException.postValue(e.message)
            }
        }
    }
}