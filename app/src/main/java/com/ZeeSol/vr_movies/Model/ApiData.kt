package com.ZeeSol.vr_movies.Model

import com.google.gson.annotations.SerializedName

data class ApiData(
    @SerializedName("dates")
    val dates: DateRange,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("resultsDetails")
    val resultsDetails: List<Movie>

)

data class DateRange(
    @SerializedName("minimum")
    val minimum: String,
    @SerializedName("maximum")
    val maximum: String
)
