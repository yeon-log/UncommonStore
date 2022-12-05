package com.example.uncommonstore.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        productDao = db.getProductList()
        getAllProductList()
    }

    private fun getAllProductList(){
        Thread{
            //22.12.05 정구현 초기 데이터가 없을 경우 데이터 삽입
            if(productDao.getProductCount()<=0){
                setData()
                productList = ArrayList(productDao.getProductList())
            } else{
                productList = ArrayList(productDao.getProductList())
            }
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

    private fun setData(){
        var dataList : MutableList<ProductEntity> = mutableListOf<ProductEntity>()
        dataList.add(ProductEntity(null,"꿀잠친구_베어춘식이", "39,000"))
        dataList.add(ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000"))
        dataList.add(ProductEntity(null, "안고자는애착바디필로우_라이언", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬 슬리퍼", "45,000"))
        dataList.add(ProductEntity(null,"혀딻은양꼬 페이스 러그", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬&뚱꼬 마우스패드", "39,000"))
        dataList.add(ProductEntity(null,"꿀잠친구_베어춘식이", "39,000"))
        dataList.add(ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000"))
        dataList.add(ProductEntity(null, "안고자는애착바디필로우_라이언", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬 슬리퍼", "45,000"))
        dataList.add(ProductEntity(null,"혀딻은양꼬 페이스 러그", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬&뚱꼬 마우스패드", "39,000"))
        dataList.add(ProductEntity(null,"꿀잠친구_베어춘식이", "39,000"))
        dataList.add(ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000"))
        dataList.add(ProductEntity(null, "안고자는애착바디필로우_라이언", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬 슬리퍼", "45,000"))
        dataList.add(ProductEntity(null,"혀딻은양꼬 페이스 러그", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬&뚱꼬 마우스패드", "39,000"))
        dataList.add(ProductEntity(null,"꿀잠친구_베어춘식이", "39,000"))
        dataList.add(ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000"))
        dataList.add(ProductEntity(null, "안고자는애착바디필로우_라이언", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬 슬리퍼", "45,000"))
        dataList.add(ProductEntity(null,"혀딻은양꼬 페이스 러그", "39,000"))
        dataList.add(ProductEntity(null, "혀딻은양꼬&뚱꼬 마우스패드", "39,000"))
        dataList.forEach{data ->
            productDao.insertProduct(data)
        }
    }

    private fun convertImage(){
    }
    override fun onRestart() {
        super.onRestart()
        getAllProductList()
    }

}