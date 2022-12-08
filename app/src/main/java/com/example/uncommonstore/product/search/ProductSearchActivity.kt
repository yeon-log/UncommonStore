package com.example.uncommonstore.product.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityProductSearchBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.ProductDetailActivity
import com.example.uncommonstore.product.ProductRecyclerViewAdapter
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity

class ProductSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductSearchBinding
    private lateinit var db : AppDatabase
    private lateinit var adapter : ProductRecyclerViewAdapter
    private lateinit var productDao : ProductDao
    private lateinit var productList : ArrayList<ProductEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)

        db = AppDatabase.getInstance(this)!!
        productDao = db.ProductDao()

        //툴바 설절
        setToolBar()

        //searchview 설정
        val searchView = binding.searchView
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // 검색 버튼 누를 때 호출
            override fun onQueryTextSubmit(word: String?): Boolean {
                if (word != null) {
                    search(word)
                }
                return true
            }

            override fun onQueryTextChange(word: String?): Boolean {
                // 검색창에서 글자가 변경이 일어날 때마다 호출
                if(word != null){
                    search(word)
                }
                return true
            }
        })
        setContentView(binding.root)
    }

    private fun search(word : String){
        //검색어
        val searchWord = "$word"
        Thread {
            productList = productDao.getProductSearch(searchWord) as ArrayList<ProductEntity>
            if(productList.size==0){
                runOnUiThread {
                    binding.tvNoResult.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                    //검색 결과 갯수 반환
                    binding.tvTotalProduct.text = "총 "+productList.size+"개"
                }
            }else{
                runOnUiThread {
                    binding.tvNoResult.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                setRecyclerView(productList)
            }

        }.start()

    }
    private fun setRecyclerView(productList : ArrayList<ProductEntity>){
        runOnUiThread {
            //어댑터 등록
            adapter = ProductRecyclerViewAdapter(productList)
            binding.recyclerView.adapter = adapter
            //검색 결과 갯수 반환
            binding.tvTotalProduct.text = "총 " + adapter.itemCount.toString() +"개"
            //그리드 레이아웃 매니저 지정
            binding.recyclerView.layoutManager = GridLayoutManager(this,3)
            //상품 상세페이지로 이동
            adapter.setOnItemClickListener(object : ProductRecyclerViewAdapter.OnItemClickListener{
                override fun onItemClick(v: View, product: ProductEntity, pos : Int) {
                    Intent(this@ProductSearchActivity, ProductDetailActivity::class.java).apply {
                        putExtra("product", product)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }
                }
            })

        }
    }

    //툴바설정함수
    private fun setToolBar(){
        setSupportActionBar(binding.productToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.go_back)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item.itemId){
            android.R.id.home->{ // 뒤로 가기 버튼을 눌렀을 때
                finish()
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //툴바 부분 끝

}