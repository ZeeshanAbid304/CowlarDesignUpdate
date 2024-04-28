package com.ZeeSol.vr_movies.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ZeeSol.vr_movies.Model.MainViewModel
import com.ZeeSol.vr_movies.R
import com.ZeeSol.vr_movies.Room.MovieEntity
import com.ZeeSol.vr_movies.databinding.FragmentBookTicketBinding

class BookTicketFragment : Fragment() {
    private var _binding: FragmentBookTicketBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookTicketBinding.inflate(inflater, container, false)



        val movie: MovieEntity = BookTicketFragmentArgs.fromBundle(requireArguments()).movie
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Set the movie details in the XML views
        binding.title.text = movie.title
        binding.releasedate.text = movie.releaseDate


        binding.backButtonBooking.setOnClickListener {

            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.selectseat.setOnClickListener {
            val action = BookTicketFragmentDirections.actionBookTicketFragmentToPaymentFragment(movie)
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}