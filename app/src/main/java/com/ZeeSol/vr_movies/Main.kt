package com.ZeeSol.vr_movies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ZeeSol.vr_movies.Adapter.ViewPagerAdapter
import com.ZeeSol.vr_movies.databinding.FragmentMainBinding
import me.ibrahimsn.lib.SmoothBottomBar

class Main : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var viewPager: ViewPager2? = null
    private var bottomBar: SmoothBottomBar? = null

    private val fragmentTitles = listOf("Home", "Search", "Favorite","Account") // Add titles for each fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view: View = binding.root

        viewPager = binding.viewPager
        bottomBar = binding.bottomBar

        val pagerAdapter = ViewPagerAdapter(requireActivity())
        viewPager!!.adapter = pagerAdapter

        viewPager!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomBar!!.itemActiveIndex = position
                updateHomeText(position)
            }
        })

        bottomBar!!.setOnItemSelectedListener { index ->
            viewPager!!.setCurrentItem(index, true)
            updateHomeText(index)
        }

        // Set click listener for the search button
        binding.searchbtn.setOnClickListener {
            // Navigate to the search fragment or perform the desired action
            // Replace R.id.action_searchFragment with the appropriate action ID

        }

        return view
    }

    private fun updateHomeText(position: Int) {
        binding.home.text = fragmentTitles[position]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}