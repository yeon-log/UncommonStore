package com.example.uncommonstore.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uncommonstore.R
import com.example.uncommonstore.payment.db.CardEntity

class FragThirdActivity(var card: CardEntity?) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_card_frame_3p, container, false) as ViewGroup
        var tv_cardName_empty3: TextView = view.findViewById(R.id.tv_card_name_empty3)
        var tv_cardName3: TextView = view.findViewById(R.id.tv_card_name3)

        if (card != null) {
            tv_cardName3.text = card!!.cardName.toString()
            tv_cardName3.visibility = View.VISIBLE
            tv_cardName_empty3.visibility = View.INVISIBLE
        } else {
            tv_cardName_empty3.text = "" // 빈문자열로
        }
        return view
    }
}