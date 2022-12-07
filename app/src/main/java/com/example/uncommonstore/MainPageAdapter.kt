package com.example.uncommonstore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPageAdapter (private val fragmentActivity: FragmentActivity,
    private  val fragments: List<Fragment>): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> return MainOneFragment()
            1 -> return MainTwoFragment()
            else -> return MainThreeFragment()
        }
    }
}