package com.ZeeSol.vr_movies.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ZeeSol.vr_movies.Fragments.AccountFragment
import com.ZeeSol.vr_movies.Fragments.HomeFragment
import com.ZeeSol.vr_movies.Fragments.FavFragment
import com.ZeeSol.vr_movies.Fragments.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4 // Number of fragments

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> FavFragment()
            3 -> AccountFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}