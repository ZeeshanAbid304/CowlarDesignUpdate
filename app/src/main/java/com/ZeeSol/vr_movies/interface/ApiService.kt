package com.ZeeSol.vr_movies;


import com.ZeeSol.vr_movies.Model.ApiData
import com.ZeeSol.vr_movies.Model.Movie
import com.ZeeSol.vr_movies.Model.VideoListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/upcoming")
    suspend fun getMovies(@Query("api_key") apiKey: String): ApiData

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie

    @GET("3/movie/{movieId}/videos")
    suspend fun getMovieTrailers(
        @Path("movieId") movieId: Int,
        @Query("youtube_api_key") apiKey: String
    ): Movie

    @GET("3/movie/{movieId}/videos")
    suspend fun getTrendingMovieTrailers(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): VideoListDTO

    @GET("3/movie/{movieId}/images")
    suspend fun getImagesDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie
}