package com.example.uncommonstore.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uncommonstore.R
import com.example.uncommonstore.payment.db.CardEntity

class FragSecondActivity(var card: CardEntity?) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_card_frame_2p, container, false) as ViewGroup
        var tv_cardName_empty2 : TextView = view.findViewById(R.id.tv_card_name_empty2)
        var tv_cardName2 : TextView = view.findViewById(R.id.tv_card_name2)

        if(card != null){
            tv_cardName2.text = card!!.cardName.toString()
            tv_cardName2.visibility = View.VISIBLE
            tv_cardName_empty2.visibility = View.INVISIBLE
        }
        else{
            tv_cardName_empty2.text = "카드를 등록해주세요"
        }
        return view
    }
}