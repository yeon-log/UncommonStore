package com.example.uncommonstore.payment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uncommonstore.payment.FragFirstActivity
import com.example.uncommonstore.payment.FragFourthActivity
import com.example.uncommonstore.payment.FragSecondActivity
import com.example.uncommonstore.payment.FragThirdActivity

class MyAdapter(fa: FragmentActivity?, var mCount: Int) : FragmentStateAdapter(
    fa!!
) {
    override fun createFragment(position: Int): Fragment {
        val index = getRealPosition(position)
        return if (index == 0) FragFirstActivity()
        else if (index == 1) FragSecondActivity()
        else if (index == 2) FragThirdActivity()
        else FragFourthActivity()

    }

    override fun getItemCount(): Int {
        return 2000
    }

    fun getRealPosition(position: Int): Int {
        return position % mCount
    }
}