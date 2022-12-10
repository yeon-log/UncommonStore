package com.example.uncommonstore.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.uncommonstore.*
import com.example.uncommonstore.databinding.ActivityProductDetailBinding
import com.example.uncommonstore.databinding.ActivityProductOneFragmentBinding
import com.example.uncommonstore.databinding.ActivityProductThreeFragmentBinding
import com.example.uncommonstore.databinding.ActivityProductTwoFragmentBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import com.example.uncommonstore.product.pager.ProductOneFragment
import com.example.uncommonstore.product.pager.ProductThreeFragment
import com.example.uncommonstore.product.pager.ProductTwoFragment
import com.example.uncommonstore.product.pager.ViewPagerAdapter
import com.example.uncommonstore.product.search.ProductSearchActivity
import kotlinx.android.synthetic.main.activity_product_detail.*
/*****************************************************
 * @function : ProductDetailActivity
 * @author : 정구현
 * @Date : 2022.12.06 생성
 *****************************************************/
class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    //썸네일 ViewPager2 적용을 위한 Fragement 요소
    private lateinit var bindingFragmentOne : ActivityProductOneFragmentBinding
    private lateinit var bindingFragmentTwo : ActivityProductTwoFragmentBinding
    private lateinit var bindingFragmentThree : ActivityProductThreeFragmentBinding

    //추천 상품을 위한 추가
    private lateinit var db:AppDatabase
    private lateinit var productDao: ProductDao
    private lateinit var productList: ArrayList<ProductEntity>
    private lateinit var adapter: ProductRecyclerViewAdapter

    // ViewPager2 인디케이터
    private var dots = arrayOfNulls<TextView>(3)


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        bindingFragmentOne = ActivityProductOneFragmentBinding.inflate(layoutInflater)
        bindingFragmentTwo = ActivityProductTwoFragmentBinding.inflate(layoutInflater)
        bindingFragmentThree = ActivityProductThreeFragmentBinding.inflate(layoutInflater)

        //ProductListActivity로부터 전송받은 객체 초기화
        val productData = intent.getSerializableExtra("product") as ProductEntity
        val context = binding.root.context
        //DB 상 이미지들을 구분자를 이용하여 배열로 초기화
        val prodThumbnails = productData.prodThumbnail.toString().split(",")
        val prodImages = productData.prodImage.toString().split(",")

        db = AppDatabase.getInstance(this)!!
        productDao = db.ProductDao()

        //추천상품 함수
        getRecommendProductList()

        //재고확인 버튼
        binding.btnProductStock.setOnClickListener{
            binding.btnProductStock.visibility = View.INVISIBLE
            binding.productStock.visibility = View.VISIBLE
        }

        // 프래그먼트 선언
        var fragment1 = ProductOneFragment(prodThumbnails[0])
        var fragment2 = ProductTwoFragment(prodThumbnails[1])
        var fragment3 = ProductThreeFragment(prodThumbnails[2])

        // 리스트에 프래그먼트 등록
        var fragments = ArrayList<Fragment>()
        fragments.add(fragment1)
        fragments.add(fragment2)
        fragments.add(fragment3)

        // ViewPagerAdapter에 리스트 등록
        var adapter = ViewPagerAdapter(this, fragments,prodThumbnails)

        // pagerProductThumbnail에 Adapter 적용
        binding.pagerProductThumbnail.adapter = adapter

        // 화면 위치 표시 생성
        dotsIndicator()

        // 화면 변경 시 이벤트 설정
        binding.pagerProductThumbnail.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            // Circular Scrolling 기능을 구현하기 위한 현재 상태와 위치 저장할 변수 2개 선언
            var currentState = 0
            var currentPos = 0
            override fun onPageScrolled(
                position: Int,
                positionOffset:Float,
                positionOffsetPixels:Int
            ){
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position){
                    if(currentPos == 0) binding.pagerProductThumbnail.currentItem = 2
                    else if(currentPos == 2) binding.pagerProductThumbnail.currentItem = 0
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int){
                currentPos = position
                super.onPageSelected(position)
                //화면 위치 표시 색상 설정
                selectedIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }
        })


        binding.productName.text = productData.prodName.toString() //상품 이름
        binding.productPrice.text = productData.prodPrice.toString() +"원" //상품 가격
        binding.productStock.text = "남은 수량: " + productData.prodStock.toString() + " 개" // 재고
        binding.productContent.text = productData.prodContent.toString() //상품 설명

        //상품 내용 이미지
        Glide.with(context)
            .load(prodImages[0])
            .into(binding.imgProductContent1)
        Glide.with(context)
            .load(prodImages[1])
            .into(binding.imgProductContent2)

        Glide.with(context)
            .load(prodImages[2])
            .into(binding.imgProductContent3)

        Glide.with(context)
            .load(prodImages[3])
            .into(binding.imgProductContent4)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolBar()
    }

    //추천 상품 리스트 함수 (랜덤으로 6개씩 반환)
    private fun getRecommendProductList(){
        Thread{
            productList = ArrayList(productDao.getProductRandomList())
            setRecyclerView()
        }.start()
    }

    //추천 상품 리스트를 위한 RecyclerVIew
    private fun setRecyclerView() {
        runOnUiThread {
            adapter = ProductRecyclerViewAdapter(productList)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = GridLayoutManager(this,3)
            adapter.setOnItemClickListener(object : ProductRecyclerViewAdapter.OnItemClickListener{
                override fun onItemClick(v: View, product: ProductEntity, pos : Int) {
                    Intent(this@ProductDetailActivity, ProductDetailActivity::class.java).apply {
                        putExtra("product", product)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }
                }
            })
        }
    }
    // 화면 위치 표시 생성
    private fun dotsIndicator(){
        for(i: Int in 0 until 3){
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml(("&#9679"), Html.FROM_HTML_MODE_LEGACY)
            dots[i]?.textSize = 15F

            // 텍스트뷰 레이아웃 설정
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 텍스트뷰 간의 거리 조절
            params.leftMargin = 25
            // 텍스트뷰 레이아웃 적용
            dots[i]?.layoutParams = params
            // 레이아웃에 텍스트뷰 적용
            binding.dotsLayout.addView(dots[i])
        }
    }
    // 화면 위치 표시 색상 설정
    private fun selectedIndicator(position: Int){
        for(i: Int in 0 until 3){
            // 선택한 위치
            if(i == position){
                dots[i]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.cherry_red))
            }//선택안된거
            else{
                dots[i]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.background_gray))
            }
        }
    }

    //이 부분 부터 툴바 부분
    private fun setToolBar(){
        setSupportActionBar(product_detail_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.go_back)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                val intentSearch = Intent(this@ProductDetailActivity, ProductListActivity::class.java)
                startActivity(intentSearch)
            }
            R.id.action_search->{ // 검색버튼 눌렀을때
                val intentSearch = Intent(this@ProductDetailActivity, ProductSearchActivity::class.java)
                startActivity(intentSearch)
            }
            R.id.action_home->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //툴바 부분 끝

    override fun onRestart() {
        super.onRestart()
        getRecommendProductList()
    }

}