package com.example.flogger.adapter

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flogger.fragment.HistoryFragment
import com.example.flogger.fragment.RoutineListFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    @Override
    override fun getItemCount(): Int {
        return 2;
    }

    @NonNull
    @Override
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RoutineListFragment()
            1 -> HistoryFragment()
            else -> RoutineListFragment()
        }
    }

}