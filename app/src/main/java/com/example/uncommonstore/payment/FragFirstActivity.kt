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

class FragFirstActivity(var card : CardEntity): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_card_frame_1p, container, false) as ViewGroup
        var tv_cardName : TextView = view.findViewById(R.id.tv_card_name1)
        tv_cardName.text = card.cardName.toString()
        return view
    }
}