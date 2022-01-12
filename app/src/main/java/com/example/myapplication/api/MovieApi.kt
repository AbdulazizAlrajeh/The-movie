package com.example.myapplication.api

import com.example.myapplication.models.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    /***
     * REQUEST METHOD
    Every method must have an HTTP annotation that provides the request method and relative URL.
    There are eight built-in annotations: HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD.
    The relative URL of the resource is specified in the annotation.
     */

    /**
     *  GET is used to retrieve and request data from a specified resource in a server
     */

    @GET("/3/search/movie?api_key=f2c3e1dd3f1a1bc95afab70c5742f2dd&query=all")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MoviesModel>


}