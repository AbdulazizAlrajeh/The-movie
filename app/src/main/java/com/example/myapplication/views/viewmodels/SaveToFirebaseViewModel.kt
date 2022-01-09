package com.example.myapplication.views.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Result
import com.example.myapplication.repository.FirebaseServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "WatchLaterViewModel"

class SaveToFirebaseViewModel : ViewModel() {

    private val firebaseRepository = FirebaseServiceRepository

    val saveToFirebaseLiveDataCorrect = MutableLiveData<String>()
    val saveToFirebaseLiveDataException = MutableLiveData<String>()


    fun callSaveMovieToFirebase(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseRepository.saveMovie(result)

                response.addOnCompleteListener {
                    if (response.isSuccessful) {
                        Log.d(TAG, it.toString())
                        saveToFirebaseLiveDataCorrect.postValue("Successfully")

                    } else {
                        // Log.d(TAG, response.message().toString())
                        Log.d(TAG, response.exception.toString())
                        saveToFirebaseLiveDataException.postValue(response.exception.toString())
                    }
                }
            } catch (e: Exception) {
                // Send Error Response to view
                saveToFirebaseLiveDataException.postValue(e.message.toString())
            }
        }
    }


}
