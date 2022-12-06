package com.example.uncommonstore.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityEventDetailBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.event.db.EventEntity

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        val eventData = intent.getSerializableExtra("event") as EventEntity
        val context = binding.root.context

        println("제목"+eventData.eventName.toString())
        binding.tvEventTitle.text = eventData.eventName.toString()
        binding.tvEventContent.text = eventData.eventContent.toString()
        Glide.with(context)
            .load(eventData.eventImage2)
            .into(binding.imgEventImage1)
        Glide.with(context)
            .load(eventData.eventImage3)
            .into(binding.imgEventImage2)
        Glide.with(context)
            .load(eventData.eventImage4)
            .into(binding.imgEventImage3)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}