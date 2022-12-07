package com.example.uncommonstore.product.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.uncommonstore.R

class ProductOneFragment(var thumbnail : String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstaceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_product_one_fragment, container, false)

        Glide.with(view.context)
            .load(thumbnail)
            .into(view.findViewById(R.id.img_product_thumbnail))

        return view
    }
}