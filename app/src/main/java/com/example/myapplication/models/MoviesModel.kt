package com.example.myapplication.models


import com.google.gson.annotations.SerializedName

data class MoviesModel(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)