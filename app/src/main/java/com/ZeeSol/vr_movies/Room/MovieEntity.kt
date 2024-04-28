package com.ZeeSol.vr_movies.Room
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val overview: String?,
    val posterPath: String?,
    val genreIds: List<Int> // List of genre IDs
):Parcelable