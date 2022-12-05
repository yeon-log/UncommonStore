package com.example.uncommonstore.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityProductAddBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity

class ProductAddActivity : AppCompatActivity() {
    lateinit var binding : ActivityProductAddBinding
    lateinit var db : AppDatabase
    lateinit var productDao: ProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        productDao = db.getProductList()

        binding.btnCompletion.setOnClickListener{
            insertProduct()
        }
    }

    /*상품 추가 함수*/
    private fun insertProduct(){
        val productName = binding.edtProductName.text.toString()
        val productPrice = binding.edtProductPrice.text.toString()

        if(productName.isBlank() || productPrice.isBlank()){
            Toast.makeText(this,"모든 항목을 채워주세요",
                Toast.LENGTH_SHORT).show()
        }else{
            Thread{
                productDao.insertProduct(ProductEntity(null, productName, productPrice))
                runOnUiThread{
                    Toast.makeText(this, "추가되었습니다.",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}