package com.example.uncommonstore.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.uncommonstore.databinding.ActivityProductDetailBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductEntity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var db:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val productData = intent.getSerializableExtra("product") as ProductEntity
        val context = binding.root.context

        binding.productName.text = productData.prodName.toString()
        binding.productPrice.text = productData.prodPrice.toString()
        binding.productStock.text = "재고: " + productData.prodStock.toString() + " 개"
        binding.productContent.text = productData.prodContent.toString()
        Glide.with(context)
            .load(productData.prodImage)
            .into(binding.imgProductThumbnail)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


}