package com.example.uncommonstore.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.uncommonstore.R
import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        val items = mutableListOf<ListViewEvent>()

        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))
        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))
        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))
        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))
        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))
        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))
        items.add(ListViewEvent(ContextCompat.getDrawable(this, R.drawable.event_bear)!!))

        val adapter = ListViewAdapter(items)
        listView.adapter = adapter
    }
}