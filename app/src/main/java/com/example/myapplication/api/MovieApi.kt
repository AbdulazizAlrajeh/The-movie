package com.example.myapplication.api

import com.example.myapplication.models.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

@GET("/search/movie?api_key=f2c3e1dd3f1a1bc95afab70c5742f2dd&query=all")
suspend fun getMovies(
    @Query("page") page:Int
):Response<MoviesModel>



}