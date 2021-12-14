package com.example.myapplication.models


import com.google.firebase.auth.FirebaseAuth
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("title")
    val title: String? = null,
    var isWatched : Boolean = false,
    var userId: String = "" ,
    var iswatchedLater : Boolean = false
)