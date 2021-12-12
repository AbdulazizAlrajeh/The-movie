package com.example.myapplication.views.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Result
import com.example.myapplication.repository.FirebaseServiceRepository

class WatchLaterViewModel:ViewModel() {

    private val firebaseRepository = FirebaseServiceRepository.get()

    val watchLaterMoviesLiveDate = MutableLiveData<String>()
    val watchLaterMoviesErrorLiveData = MutableLiveData<String>()
}