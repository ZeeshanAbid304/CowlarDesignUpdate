package com.ZeeSol.vr_movies.Fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ZeeSol.vr_movies.R
import com.ZeeSol.vr_movies.databinding.FragmentFullScreenPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
class FullScreenPlayerFragment : Fragment() {

    private lateinit var videoId: String
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var binding: FragmentFullScreenPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullScreenPlayerBinding.inflate(inflater, container, false)
        val rootView = binding.root
        youTubePlayerView = binding.youtubePlayerView
        val doneButton = binding.doneButton
        val titleoriginal = binding.titleoriginal

        doneButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trailerUrl = arguments?.getString("trailerUrl")
        val title = arguments?.getString("title")
        binding.titleoriginal.text = title

        trailerUrl?.let { url ->
            videoId = extractVideoId(url)
            if (videoId.isNotEmpty()) {
                initializeYouTubePlayer()
            } else {
                Toast.makeText(requireContext(), "Invalid YouTube video URL", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(requireContext(), "Trailer URL is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun extractVideoId(youtubeUrl: String): String {
        val uri = Uri.parse(youtubeUrl)
        return uri.getQueryParameter("v") ?: ""
    }

    private fun initializeYouTubePlayer() {
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.play()
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state == PlayerConstants.PlayerState.ENDED) {
                    requireActivity().onBackPressed()
                }
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                // Handle player errors if needed
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(youTubePlayerView)
    }

    companion object {
        fun newInstance(trailerUrl: String): FullScreenPlayerFragment {
            val fragment = FullScreenPlayerFragment()
            val args = Bundle()
            args.putString("trailerUrl", trailerUrl)
            fragment.arguments = args
            return fragment
        }
    }
}