package com.example.uncommonstore.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uncommonstore.databinding.ItemProductBinding
import com.example.uncommonstore.product.db.ProductEntity

class ProductRecyclerViewAdapter(private val productList: ArrayList<ProductEntity>)
    : RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(binding : ItemProductBinding):
        RecyclerView.ViewHolder(binding.root){
        val tv_productName = binding.tvProductName
        val tv_productPrice = binding.tvProductPrice

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
        val productData = productList[position]

        holder.tv_productName.text = productData.productName.toString()
        holder.tv_productPrice.text = productData.productPrice.toString() + "원"
    }



    override fun getItemCount(): Int {
        return productList.size
    }


}