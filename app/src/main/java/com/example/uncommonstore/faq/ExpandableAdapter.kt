package com.example.uncommonstore.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityFaqItemBinding


class ExpandableAdapter(private var itemList: List<DataItem>) : RecyclerView.Adapter<ExpandableAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: ActivityFaqItemBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityFaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(itemList[position]){
                // set name of the language from the list
                binding.tvMainName.text = this.name
                binding.tvDescription.text = this.description

                // 확장 이미지 보였다/ 사라졌다
                binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE

                // 화살표 방향 up/down
                //if(this.expand) binding.viewMoreBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24) else binding.viewMoreBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)

//                binding.expandedView.visibility = if(this.expand) {
//                    binding.viewMoreBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
//                    View.VISIBLE
//                }
//                else {
//                    binding.viewMoreBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
//                    View.GONE
//                }

                binding.viewMoreBtn.setOnClickListener {
                    this.expand = !this.expand
                    notifyDataSetChanged()
                }

//                binding.cardLayout.setOnClickListener {
//                    this.expand = !this.expand
//                    notifyDataSetChanged()
//                }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

}