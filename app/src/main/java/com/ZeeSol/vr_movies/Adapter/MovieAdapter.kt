package com.ZeeSol.vr_movies.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ZeeSol.vr_movies.R
import com.ZeeSol.vr_movies.Room.MovieEntity
import com.bumptech.glide.Glide

class MovieAdapter(private val listener: MovieClickListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<MovieEntity> = listOf()

    fun submitList(movies: List<MovieEntity>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.releaseDate)
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: MovieEntity) {
            movieTitleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .centerInside()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(posterImageView)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = movies[position]
                listener.onMovieClick(movie)
            }
        }
    }

    interface MovieClickListener {
        fun onMovieClick(movie: MovieEntity)
    }
}