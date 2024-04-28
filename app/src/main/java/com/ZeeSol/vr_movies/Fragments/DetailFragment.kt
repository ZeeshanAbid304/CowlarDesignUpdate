package com.ZeeSol.vr_movies.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ZeeSol.vr_movies.Model.MainViewModel
import com.ZeeSol.vr_movies.R
import com.ZeeSol.vr_movies.Room.MovieEntity
import com.ZeeSol.vr_movies.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the movie entity from arguments
        val movie: MovieEntity = DetailFragmentArgs.fromBundle(requireArguments()).movie
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Set the movie details in the XML views
        binding.titleoriginal.text = movie.title
        binding.releasedate.text = movie.releaseDate
        binding.overviewTextView.text = movie.overview
        viewModel.fetchMovieTrailer(movie.id,movie.title)
        binding.trailer.setOnClickListener {


            fetchTrailerUrl(movie.id,movie.title)
        }
        binding.backButton.setOnClickListener {

            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
        binding.getTicket.setOnClickListener {

            val action = DetailFragmentDirections.actionDetailFragmentToBookTicketFragment(movie)
            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_detailFragment_to_bookTicketFragment)
        }
        // Load the image into the ImageView using Glide

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .centerInside()
            .placeholder(R.drawable.loading)
            .error(R.drawable.loading)
            .into(binding.toolbarImage)

        // Set the genre IDs in the TextView
        val genreIdsText = genreIdsToString(movie.genreIds)
        binding.genreIdsTextView.text = genreIdsText
    }
    private fun fetchTrailerUrl(movieId: Int,title: String) {



        viewModel.fetchMovieTrailer(movieId,title)
        viewModel.trailerUrl.observe(viewLifecycleOwner) { trailerUrl ->
            // Dismiss loading indicator


            trailerUrl?.let { url ->
                openFullScreenPlayer(url,title)
            } ?: run {
                Toast.makeText(requireContext(), "No trailer available", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun openFullScreenPlayer(trailerUrl: String,title :String) {
        val action = DetailFragmentDirections.actionDetailFragmentToFullScreenPlayerFragment(trailerUrl,title)
        findNavController().navigate(action)

    }
    private fun genreIdsToString(genreIds: List<Int>): String {
        val genreIdsString = StringBuilder()
        for (genreId in genreIds) {
            genreIdsString.append("$genreId, ")
        }
        // Remove the trailing comma and space
        if (genreIdsString.isNotEmpty()) {
            genreIdsString.deleteCharAt(genreIdsString.length - 1)
            genreIdsString.deleteCharAt(genreIdsString.length - 1)
        }
        return genreIdsString.toString()
    }
}