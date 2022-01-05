package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.api.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val BASE_URL = "https://api.themoviedb.org"

object ApiServiceRepository {

    // Retrofit.Builder
    // And we need to specify a factory for deserializing the response using the Gson library
    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitApi = retrofitService.create(MovieApi::class.java)


    suspend fun getMovies(page: Int) = retrofitApi.getMovies(page)

}