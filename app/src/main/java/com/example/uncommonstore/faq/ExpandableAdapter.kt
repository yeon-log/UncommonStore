package com.example.uncommonstore.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityFaqItemBinding
/*****************************************************
 * @function : ExpandableAdapter(FAQ)
 * @author : 김민석
 * @Date : 2022.12.06 생성
 *****************************************************/

class ExpandableAdapter(private var itemList: List<DataItem>) : RecyclerView.Adapter<ExpandableAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ActivityFaqItemBinding) : RecyclerView.ViewHolder(binding.root)

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
                binding.viewMoreBtn.setOnClickListener {
                    this.expand = !this.expand
                    notifyDataSetChanged()
                }
            }
        }
    }
    override fun getItemCount(): Int = itemList.size
}