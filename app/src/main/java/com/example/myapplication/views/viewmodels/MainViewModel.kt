package com.example.myapplication.views.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Result
import com.example.myapplication.repository.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    // Getting instance from Api Service Repository with companion object function
    private val repositoryAPI = ApiServiceRepository.get()

    var page = 1

    val moviesLiveDate = MutableLiveData<List<Result>>()
    val moviesErrorLiveData = MutableLiveData<String>()

    fun callGetMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repositoryAPI.getMovies(page)
                if (response.isSuccessful) {
                    response.body()?.run {

                        Log.d(TAG, this.toString())

                        // Send Response to view
                        moviesLiveDate.postValue(results)

                    }

                    Log.d(TAG, response.message())


                } else {
                    Log.d(TAG, response.message().toString())

                    moviesErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                // Send Error Response to view
                moviesErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}