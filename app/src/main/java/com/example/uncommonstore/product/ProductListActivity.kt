package com.example.uncommonstore.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uncommonstore.MainActivity
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityProductListBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import com.example.uncommonstore.product.search.ProductSearchActivity
import kotlinx.android.synthetic.main.activity_product_list.*
/*****************************************************
 * @function : ProductListActivity
 * @author : 정구현
 * @Date : 2022.12.05 생성
 *****************************************************/
class ProductListActivity : AppCompatActivity()    {

    private lateinit var binding:ActivityProductListBinding
    private lateinit var db : AppDatabase
    private lateinit var productDao: ProductDao
    private lateinit var productList: ArrayList<ProductEntity>
    private lateinit var adapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!

        productDao = db.ProductDao()

        //툴바 사용을 위한 함수
        setToolBar()

        //상품 리스트 로드 함수
        getAllProductList()
    }

    //product 객체에서 상품 리스트를 받아온다.
    private fun getAllProductList(){
        Thread{
            productList = ArrayList(productDao.getProductList())
            setRecyclerView() //recycler view에서 리스트화 시킴
        }.start()
    }

    private fun setRecyclerView() {
        runOnUiThread {
            adapter = ProductRecyclerViewAdapter(productList)
            binding.recyclerView.adapter = adapter
            binding.tvTotalProduct.text = "총 " + adapter.itemCount.toString() +"개" //총 상품수
            binding.recyclerView.layoutManager = GridLayoutManager(this,3) //그리드 레이아웃으로 지정

            //상품을 눌렀을때 상세페이지로 객체를 전송
            adapter.setOnItemClickListener(object : ProductRecyclerViewAdapter.OnItemClickListener{
                override fun onItemClick(v: View, product: ProductEntity, pos : Int) {
                    Intent(this@ProductListActivity, ProductDetailActivity::class.java).apply {
                        putExtra("product", product)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //ProductListAcitivity -> ProductDetailActivity로 선택한 객체를 전송
                    }.run { startActivity(this) }
                }

            })
        }
    }

    //이 부분 부터 툴바 부분 12.08 구영모 추가
    private fun setToolBar(){
        setSupportActionBar(product_toolbar)
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
                val intentSearch = Intent(this@ProductListActivity, MainActivity::class.java)
                startActivity(intentSearch)
            }
            R.id.action_search->{
                //22.12.09 정구현 추가: ProductSearchActivity로 전환
                val intentSearch = Intent(this@ProductListActivity, ProductSearchActivity::class.java)
                startActivity(intentSearch)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //툴바 부분 끝

    override fun onRestart() {
        super.onRestart()
        getAllProductList()
    }



}