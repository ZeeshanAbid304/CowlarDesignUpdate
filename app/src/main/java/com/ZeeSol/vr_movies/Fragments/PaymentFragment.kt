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
import com.ZeeSol.vr_movies.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val movie: MovieEntity = PaymentFragmentArgs.fromBundle(requireArguments()).movie
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Set the movie details in the XML views
        binding.title.text = movie.title
        binding.releasedate.text = movie.releaseDate
        binding.backButtonPayment.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.proceed.setOnClickListener {
            Toast.makeText(requireContext(), "Movie Booked", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}