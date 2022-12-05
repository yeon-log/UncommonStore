package com.example.uncommonstore.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uncommonstore.databinding.ItemProductBinding
import com.example.uncommonstore.product.db.ProductEntity

class ProductRecyclerViewAdapter(private val productList: ArrayList<ProductEntity>)
    : RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(private val binding : ItemProductBinding):
        RecyclerView.ViewHolder(binding.root){

        private val context = binding.root.context

        fun bind(product:ProductEntity){
            binding.tvProductName.text = product.prodName
            binding.tvProductPrice.text = product.prodPrice + "원"

            println("image : ${product.prodImage}" )

            //글라이드로 이미지 삽입
            Glide.with(context)
                .load(product.prodImage)
                .into(binding.imgProductThumbnail)
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


}