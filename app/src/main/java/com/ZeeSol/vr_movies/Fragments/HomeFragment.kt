package com.ZeeSol.vr_movies.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ZeeSol.vr_movies.Adapter.MovieAdapter
import com.ZeeSol.vr_movies.Model.MainViewModel
import com.ZeeSol.vr_movies.Room.MovieEntity
import com.ZeeSol.vr_movies.databinding.FragmentHomeBinding

import androidx.navigation.fragment.findNavController
import com.ZeeSol.vr_movies.MainDirections
import com.ZeeSol.vr_movies.R


class HomeFragment : Fragment() , MovieAdapter.MovieClickListener{
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        movieAdapter = MovieAdapter(this)
        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }

        viewModel.fetchMoviesIfNeeded()

        viewModel.movies.observe(viewLifecycleOwner) { movieEntities ->
            movieAdapter.submitList(movieEntities)
        }
    }

    override fun onMovieClick(movie: MovieEntity) {
        // Create an action from HomeFragment to DetailFragment and pass arguments if needed
        val action = MainDirections.actionHome2ToDetailFragment2(movie)
        findNavController().navigate(action)
        // Navigate using the action
//        findNavController().navigate(R.id.action_home2_to_detailFragment2)
    }
}