package com.example.uncommonstore.product.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (private val fragmentActivity: FragmentActivity,
                       private  val fragments: List<Fragment>,
                        private val thumbnail : List<String>): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> return ProductOneFragment(thumbnail[0])
            1 -> return ProductTwoFragment(thumbnail[1])
            else -> return ProductThreeFragment(thumbnail[2])
        }
    }
}