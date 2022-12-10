package com.example.uncommonstore.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uncommonstore.R
import com.example.uncommonstore.payment.db.CardEntity

/*****************************************************
 * @function : FragFourthActivity
 * @author : 심지연
 * @Date : 2022.12.06 생성
 *****************************************************/

class FragFourthActivity(var card: CardEntity?) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_card_frame_4p, container, false) as ViewGroup
        var tv_cardName_empty4: TextView = view.findViewById(R.id.tv_card_name_empty4)
        var tv_cardName4: TextView = view.findViewById(R.id.tv_card_name4)

        if (card != null) {
            tv_cardName4.text = card!!.cardName.toString()
            tv_cardName4.visibility = View.VISIBLE
            tv_cardName_empty4.visibility = View.INVISIBLE
        } else {
            tv_cardName_empty4.text = "" // 빈문자열로
        }
        return view
    }
}