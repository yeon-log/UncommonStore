package com.example.uncommonstore.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uncommonstore.R
import com.example.uncommonstore.payment.db.CardEntity
import kotlinx.android.synthetic.main.activity_add_card.view.*
import kotlinx.android.synthetic.main.activity_card_frame_1p.*
import javax.annotation.Nullable

class FragFirstActivity(var card : CardEntity?): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_card_frame_1p, container, false) as ViewGroup
        var tv_cardName_empty1 : TextView = view.findViewById(R.id.tv_card_name_empty1)
        var tv_cardName1 : TextView = view.findViewById(R.id.tv_card_name1)

        if(card != null){
            tv_cardName1.text = card!!.cardName.toString()
            tv_cardName1.visibility = View.VISIBLE
            tv_cardName_empty1.visibility = View.INVISIBLE
        }
        else{
            tv_cardName_empty1.text = "" // 빈문자열로
        }
        return view
    }
}