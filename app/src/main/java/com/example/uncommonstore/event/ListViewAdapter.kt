package com.example.uncommonstore.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.uncommonstore.R
import kotlinx.android.synthetic.main.custom_list_event.view.*

class ListViewAdapter(private val items: MutableList<ListViewEvent>): BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(position: Int): ListViewEvent = items[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.custom_list_event, parent, false)

        val item: ListViewEvent = items[position]
        convertView!!.image_title.setImageDrawable(item.icon)

        return convertView
    }
}