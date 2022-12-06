package com.example.uncommonstore.QRCodeReader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.uncommonstore.databinding.ActivityQrReaderResultBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.ProductDetailActivity
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
        val result = intent.getStringExtra("msg") ?: "데이터가 존재하지 않습니다."
        println(result.toString())
        Log.d("logintest","이것은 테스트입니다.")
        setUI(result)// UI를 초기화 합니다.

        db = AppDatabase.getInstance(this)!!
        val r = Runnable {
            // 데이터에 읽고 쓸때는 쓰레드 사용
            productDao = db.ProductDao()
            product = productDao.getProductById(result.toInt())
            println(product.prodName)
            Intent(this@QrReaderResultActivity, ProductDetailActivity::class.java).apply {
                putExtra("product", product)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { startActivity(this) }
        }
        val thread = Thread(r)
        thread.start()



    }
    private fun setUI(result: String) {
        binding.tvContent.text = result // 넘어온 QR 코드 속 데이터를 텍스트뷰에 설정합니다.
        binding.btnGoBack.setOnClickListener {
            finish() // 돌아가기 버튼을 눌러줬을 때 ResultActivity를 종료합니다.
        }
    }
}