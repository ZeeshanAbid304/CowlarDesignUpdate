package com.ZeeSol.vr_movies.Model

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ZeeSol.vr_movies.ApiService
import com.ZeeSol.vr_movies.R
import com.ZeeSol.vr_movies.Room.AppDatabase
import com.ZeeSol.vr_movies.Room.MovieEntity
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao = AppDatabase.getDatabase(application).movieDao()
    private val apiKey: String = readApiKey(application)

    private val apiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()
        .create(ApiService::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun readApiKey(context: Context): String {
        val resources: Resources = context.resources
        val rawResource = resources.openRawResource(R.raw.config)
        val properties = Properties()
        properties.load(rawResource)
        return properties.getProperty("api_key")
    }

    val movies: LiveData<List<MovieEntity>> = movieDao.getAllMovies()
    private val _trailerUrl = MutableLiveData<String?>()
    private var savedTrailerUrl: String? = null
    val trailerUrl: LiveData<String?> = _trailerUrl

    fun fetchMoviesIfNeeded() {
        viewModelScope.launch {
            try {
                val apiData = apiService.getMovies(apiKey)
                Log.e(TAG, "api key::"+apiKey)
                val movies = apiData.results.map { movie ->
                    MovieEntity(
                        id = movie.id,
                        title = movie.title,
                        releaseDate = movie.releaseDate,
                        overview = movie.overview,
                        posterPath = movie.posterPath,
                        genreIds = movie.genreIds
                    )
                }
                movieDao.insertAll(movies)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Failed to fetch movies: ${e.message}")
            }
        }
    }

    fun fetchMovieTrailer(movieId: Int, title: String) {
        viewModelScope.launch {
            try {
                val trailersResponse = apiService.getTrendingMovieTrailers(movieId, apiKey)

                val trailerKey = trailersResponse.results?.firstOrNull()?.key
                if (trailerKey != null) {
                    Log.e(TAG, "youtube::"+apiKey)
                    val trailerUrl = "https://www.youtube.com/watch?v=$trailerKey"
                    _trailerUrl.value = trailerUrl
                    savedTrailerUrl = trailerUrl
                } else {
                    _trailerUrl.value = null // No trailers found
                }
            } catch (e: Exception) {
                // Handle error
                Log.e(TAG, "Error fetching movie trailer", e)
                _trailerUrl.value = null
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}