package com.example.uncommonstore.QRCodeReader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uncommonstore.databinding.ActivityQrReaderResultBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity

class QrReaderResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityQrReaderResultBinding
    private lateinit var product : ProductEntity
    private lateinit var db : AppDatabase
    private lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrReaderResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db = AppDatabase.getInstance(this)!!
        productDao = db.ProductDao()

        val result = intent.getStringExtra("msg") ?: "데이터가 존재하지 않습니다."
        product = productDao.getProductById(result.toInt())
        println(product.prodName)



        setUI(result)// UI를 초기화 합니다.
    }
    private fun setUI(result: String) {
        binding.tvContent.text = result // 넘어온 QR 코드 속 데이터를 텍스트뷰에 설정합니다.
        binding.btnGoBack.setOnClickListener {
            finish() // 돌아가기 버튼을 눌러줬을 때 ResultActivity를 종료합니다.
        }
    }
}