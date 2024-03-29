package com.example.uncommonstore.product.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
/*****************************************************
 * @function : ViewPagerAdapter
 * @author : 정구현
 * @Date : 2022.12.07 생성
 *****************************************************/
class ViewPagerAdapter (private val fragmentActivity: FragmentActivity,
                       private  val fragments: List<Fragment>,
                        private val thumbnail : List<String>): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {  //총 상품 수 반환
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){// 각 위치에 fragment 요소 지정
            0 -> return ProductOneFragment(thumbnail[0])
            1 -> return ProductTwoFragment(thumbnail[1])
            else -> return ProductThreeFragment(thumbnail[2])
        }
    }
}