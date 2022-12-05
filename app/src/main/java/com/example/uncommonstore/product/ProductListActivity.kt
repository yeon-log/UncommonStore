package com.example.uncommonstore.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityProductListBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import java.util.Objects

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProductListBinding

    private lateinit var db : AppDatabase
    private lateinit var productDao: ProductDao
    private lateinit var productList: ArrayList<ProductEntity>
    private lateinit var adapter: ProductRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{
            val intent = Intent(this, ProductAddActivity::class.java)
            startActivity(intent)
        }

        db = AppDatabase.getInstance(this)!!
        productDao = db.ProductDao()
        getAllProductList()
    }

    private fun getAllProductList(){
        Thread{
            productList = ArrayList(productDao.getProductList())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        runOnUiThread {
            adapter = ProductRecyclerViewAdapter(productList)
            binding.recyclerView.adapter = adapter
            binding.tvTotalProduct.text = "총 " + adapter.itemCount.toString() +"개"
            binding.recyclerView.layoutManager = GridLayoutManager(this,3)
        }

    }

    override fun onRestart() {
        super.onRestart()
        getAllProductList()
    }

}