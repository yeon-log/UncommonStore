package com.example.uncommonstore.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uncommonstore.databinding.ActivityProductDetailBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductEntity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var db:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        val productData = intent.getSerializableExtra("product") as ProductEntity

        binding.productName.text = productData.prodName.toString()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


}