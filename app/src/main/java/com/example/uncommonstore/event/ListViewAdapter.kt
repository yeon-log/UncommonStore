package com.example.uncommonstore.event

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.uncommonstore.databinding.CustomListEventBinding
import com.example.uncommonstore.event.db.EventEntity


class ListViewAdapter(private val eventList : ArrayList<EventEntity>)
    : RecyclerView.Adapter<ListViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(private val binding : CustomListEventBinding):
        RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context

        fun bind(event:EventEntity){
            Glide.with(context)
                .load(event.eventImage)
                .into(binding.imageTitle)
        }
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewAdapter.MyViewHolder {
        val binding : CustomListEventBinding =
            CustomListEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position : Int){
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}