package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.api.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val BASE_URL = "https://api.themoviedb.org"

class ApiServiceRepository(val context: Context) {

    // Retrofit.Builder
    // And we need to specify a factory for deserializing the response using the Gson library
    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitApi = retrofitService.create(MovieApi::class.java)


    suspend fun getMovies(page: Int) = retrofitApi.getMovies(page)


    /*
     * this companion object for restricts the instantiation of a class to one "single" instance.
     * This is useful when exactly one object is needed to coordinate actions across the system.
     * */

    companion object {
        private var instance: ApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get(): ApiServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }
}