package com.example.uncommonstore.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.uncommonstore.*
import com.example.uncommonstore.databinding.ActivityProductDetailBinding
import com.example.uncommonstore.databinding.ActivityProductOneFragmentBinding
import com.example.uncommonstore.databinding.ActivityProductThreeFragmentBinding
import com.example.uncommonstore.databinding.ActivityProductTwoFragmentBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.product.db.ProductEntity
import com.example.uncommonstore.product.pager.ProductOneFragment
import com.example.uncommonstore.product.pager.ProductThreeFragment
import com.example.uncommonstore.product.pager.ProductTwoFragment
import com.example.uncommonstore.product.pager.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.view.*

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var bindingFragmentOne : ActivityProductOneFragmentBinding
    private lateinit var bindingFragmentTwo : ActivityProductTwoFragmentBinding
    private lateinit var bindingFragmentThree : ActivityProductThreeFragmentBinding
    private lateinit var db:AppDatabase
    // 텍스트뷰 -> 화면 위치 표시
    private var dots = arrayOfNulls<TextView>(3)


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        bindingFragmentOne = ActivityProductOneFragmentBinding.inflate(layoutInflater)
        bindingFragmentTwo = ActivityProductTwoFragmentBinding.inflate(layoutInflater)
        bindingFragmentThree = ActivityProductThreeFragmentBinding.inflate(layoutInflater)

        val productData = intent.getSerializableExtra("product") as ProductEntity
        val context = binding.root.context
        val prodThumbnails = productData.prodThumbnail.toString().split(",")
        val prodImages = productData.prodImage.toString().split(",")


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



        binding.productName.text = productData.prodName.toString()
        binding.productPrice.text = productData.prodPrice.toString() +"원"
        binding.productStock.text = "남은 수량: " + productData.prodStock.toString() + " 개"
        binding.productContent.text = productData.prodContent.toString()

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

    // 화면 위치 표시 생성
    private fun dotsIndicator(){
        for(i: Int in 0 until 3){
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml(("&#9679"), Html.FROM_HTML_MODE_LEGACY)
            dots[i]?.textSize = 25F

            // 텍스트뷰 레이아웃 설정
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 텍스트뷰 간의 거리 조절
            params.leftMargin = 30
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
                finish()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_search->{
                Log.d("이거는 구현이가","알아서 하겠지")
            }
            R.id.action_home->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //툴바 부분 끝
}