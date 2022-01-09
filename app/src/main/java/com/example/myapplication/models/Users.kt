package com.example.myapplication.models

import com.google.firebase.auth.FirebaseAuth

data class Users(
    var firstName:String = "",
    var lastName:String = "",
    var email:String = "",
    var userId:String ="",
    var imageUsers:String = "https://firebasestorage.googleapis.com/v0/b/the-movie-10cd7.appspot.com/o/${FirebaseAuth.getInstance().uid.toString()}?alt=media&token=c7dc37fa-7127-4113-8c83-0ebfcf36896c"
    ,

)
