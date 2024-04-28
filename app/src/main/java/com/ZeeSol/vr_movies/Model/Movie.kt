package com.ZeeSol.vr_movies.Model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("budget")
    val budget: Long,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("status")
    val status: String,
    @SerializedName("videos")
    val videos: VideoListDTO,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("backdrops")
    val backdrops: List<Image>? = null,
    @SerializedName("logos")
    val logos: List<Image>? = null,
    @SerializedName("posters")
    val posters: List<Image>? = null,
    val voteCount: Int,
    val writers: List<String>, // List of writers
    val director: String,

)
data class Image(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double,
    val height: Int,
    @SerializedName("iso_639_1")
    val isoLanguage: String?,
    @SerializedName("file_path")
    val filePath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    val width: Int
)