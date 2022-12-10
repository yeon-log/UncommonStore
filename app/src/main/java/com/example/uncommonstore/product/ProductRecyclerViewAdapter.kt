package com.example.uncommonstore.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uncommonstore.databinding.ItemProductBinding
import com.example.uncommonstore.product.db.ProductEntity

/*****************************************************
 * @function : ProductRecyclerViewAdapter
 * @author : 정구현
 * @Date : 2022.12.05 생성
 *****************************************************/

class ProductRecyclerViewAdapter(private val productList: ArrayList<ProductEntity>)
    : RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(private val binding : ItemProductBinding):
        RecyclerView.ViewHolder(binding.root){

        private val context = binding.root.context

        fun bind(product:ProductEntity){
            binding.tvProductName.text = product.prodName
            binding.tvProductPrice.text = product.prodPrice + "원"

            val images = product.prodThumbnail.toString().split(",")

            //글라이드로 이미지 삽입
            Glide.with(context)
                .load(images[0])
                .into(binding.imgProductThumbnail)

            //클릭 이벤트
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,product,pos)
                }
            }
        }

        val root = binding.root


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemProductBinding =
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    //클릭이벤트 인터페이스 추가
    interface OnItemClickListener{
        fun onItemClick(v:View, data: ProductEntity, pos : Int)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

}