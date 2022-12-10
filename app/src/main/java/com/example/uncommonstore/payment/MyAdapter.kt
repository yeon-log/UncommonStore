package com.example.uncommonstore.payment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uncommonstore.payment.db.CardEntity

/*****************************************************
 * @function : MyAdapter
 * @author : 심지연
 * @Date : 2022.12.06 생성
 *****************************************************/

class MyAdapter(fa: FragmentActivity?, var mCount: Int, var cards:MutableList<CardEntity?>) : FragmentStateAdapter(
    fa!!
) {
    override fun createFragment(position: Int): Fragment {
        val index = getRealPosition(position)
        var card_len = cards.size

        // 인덱스에 따라 첫번째~네번째 카드 까지 하나씩 넣어줌
        return if (index == 0) FragFirstActivity(cards[0])
        else if (index == 1) FragSecondActivity(cards[1])
        else if (index == 2) FragThirdActivity(cards[2])
        else FragFourthActivity(cards[3])
    }

    override fun getItemCount(): Int {
        return 2000
    }

    // 카드 위치를 구하는 함수
    fun getRealPosition(position: Int): Int {
        return position % mCount
    }
}