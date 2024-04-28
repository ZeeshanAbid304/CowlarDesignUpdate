package com.ZeeSol.vr_movies

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ZeeSol.vr_movies.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.max = 100
        val duration = 6000L // 6 seconds
        val interval = 100L // update interval in milliseconds

        // Calculate the progress update step
        val step = (interval.toFloat() / duration.toFloat()) * 100

        // Set initial progress to 0
        binding.progressbar.progress = 0

        val vrMoviesString = getString(R.string.movies)
        val spannedText = Html.fromHtml(vrMoviesString, Html.FROM_HTML_MODE_COMPACT)
        binding.livetv.text = spannedText

        // Update progress in intervals until reaching 100%
        var currentProgress = 0f

        GlobalScope.launch(Dispatchers.Main) {
            while (currentProgress <= 100) {
                binding.progressbar.progress = currentProgress.toInt()
                binding.progressText.text = "${currentProgress.toInt()}%"
                currentProgress += step
                delay(interval)
            }

            // Ensure progress reaches 100% before proceeding
            binding.progressbar.progress = 100
            binding.progressText.text = "100%"

            // After showing the loading ad dialog, navigate to home fragment
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        // Replace with your navigation logic to the home fragment
        // For example:

        findNavController().navigate(R.id.action_splash_to_home2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}