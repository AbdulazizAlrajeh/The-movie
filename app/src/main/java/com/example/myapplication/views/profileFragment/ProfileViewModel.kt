package com.example.myapplication.views.profileFragment

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Result
import com.example.myapplication.models.Users
import com.example.myapplication.repository.FirebaseServiceRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProfileViewModel"
class ProfileViewModel : ViewModel() {

    private val firebaseRepository = FirebaseServiceRepository

    val userProfileLiveData = MutableLiveData<Users>()
    val userProfileLiveDataException = MutableLiveData<String>()

    val updateUserProfileLiveDataCorrect = MutableLiveData<String>()
    val updateUserProfileLiveDataException = MutableLiveData<String>()

    val uploadImageUriLiveData = MutableLiveData<Uri>()
    val uploadImageUriLiveDataException = MutableLiveData<String>()
    val uploadImageUriLiveDataCorrect = MutableLiveData<String>()
    fun callUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseRepository.getUserInfo()
                response.addOnSuccessListener {

                    val user = it.toObject<Users>()
                    Log.d(TAG,user.toString())
                    userProfileLiveData.postValue(user!!)
                }.addOnFailureListener {
                    Log.d(TAG,it.toString())
                    userProfileLiveDataException.postValue(it.message.toString())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                userProfileLiveDataException.postValue(e.message.toString())
            }


        }
    }


    fun saveUpdateUser(user:Users){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseRepository.saveUserInfo(user)
                response.addOnSuccessListener {
                    updateUserProfileLiveDataCorrect.postValue("Update Your Information")
                }.addOnFailureListener {
                   updateUserProfileLiveDataException.postValue(it.message.toString())
                }
            } catch (e: Exception) {

                updateUserProfileLiveDataException.postValue(e.message.toString())
            }


        }
    }

    fun UploadPhoto(imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = firebaseRepository.uploadImageUser(imageUri)
                response.addOnSuccessListener { taskSnapshot ->
                    Log.d(TAG, taskSnapshot.metadata?.name.toString())
                    uploadImageUriLiveData.postValue(imageUri)
                    uploadImageUriLiveDataCorrect.postValue("Successfully upload image to fire storage")

                }.addOnFailureListener {
                    Log.d(TAG, it.message.toString())
                    uploadImageUriLiveDataException.postValue(it.message.toString())
                }

            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.message.toString())
                uploadImageUriLiveDataException.postValue(e.message.toString())
            }
        }
    }
}