package com.example.uncommonstore.event

import android.view.LayoutInflater
import android.view.View
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


            //클릭 이벤트
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,event,pos)
                }
            }
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
    //클릭이벤트 인터페이스 추가
    interface OnItemClickListener{
        fun onItemClick(v: View, data: EventEntity, pos : Int)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}