package com.example.uncommonstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.uncommonstore.QRCodeReader.QRCreateActivity
import com.example.uncommonstore.QRCodeReader.QrReaderActivity
import com.example.uncommonstore.databinding.ActivityMainBinding
import com.example.uncommonstore.event.EventListActivity
import com.example.uncommonstore.faq.FaqActivity
import com.example.uncommonstore.member.AuthActivity
import com.example.uncommonstore.member.MyApplication
import com.example.uncommonstore.payment.CardActivity
import com.example.uncommonstore.product.ProductListActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_navheader.view.*



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var isNavigationOpen = false

    lateinit var mainbinding: ActivityMainBinding
    // 텍스트뷰 -> 화면 위치 표시
    private var dots = arrayOfNulls<TextView>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding 초기화
        mainbinding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainbinding.root)

        val header = navigationView.getHeaderView(0)
        header.accountTextView.text="${MyApplication.email}\n님 반갑습니다."
        setToolbar()

        navigationView.setNavigationItemSelectedListener(this)

        // 2022.12.07 김나형 추가
        // 프래그먼트 선언
        var fragment1 = MainOneFragment()
        var fragment2 = MainTwoFragment()
        var fragment3 = MainThreeFragment()

        // 리스트에 프래그먼트 등록
        var fragments = ArrayList<Fragment>()
        fragments.add(fragment1)
        fragments.add(fragment2)
        fragments.add(fragment3)

        // MainPageAdapter에 리스트를 등록
        var adapter = MainPageAdapter(this, fragments)

        // mainViewPager2에 Adapter 적용
        mainbinding.mainViewPager2.adapter = adapter

        // 화면 위치 표시 생성
        dotsIndicator()

        // 화면 변경 시 이벤트 설정
        mainbinding.mainViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            // Circular Scrolling 기능을 구현하기 위한 현재 상태와 위치 저장할 변수 2개 선언
            var currentState = 0
            var currentPos = 0
            override fun onPageScrolled(
                position: Int,
                positionOffset:Float,
                positionOffsetPixels:Int
            ){
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position){
                    if(currentPos == 0) mainbinding.mainViewPager2.currentItem = 2
                    else if(currentPos == 2) mainbinding.mainViewPager2.currentItem = 0
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
        //mainbindig.dotsIndicator.setViewPager2(mainbinding.mainViewPager2)

        // 서비스 바로가기 연결
        mainbinding.productBtn.setOnClickListener{
            var intentProduct = Intent(this, ProductListActivity::class.java)
            startActivity(intentProduct)
        }
        mainbinding.cardBtn.setOnClickListener{
            var intentcard = Intent(this, CardActivity::class.java)
            startActivity(intentcard)
        }
        mainbinding.eventBtn.setOnClickListener{
            var intentevent = Intent(this, EventListActivity::class.java)
            startActivity(intentevent)
        }
        mainbinding.faqBtn.setOnClickListener{
            var intentfaq = Intent(this, FaqActivity::class.java)
            startActivity(intentfaq)
        }
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

            // 텍스트튜 간의 거리 조절
            params.leftMargin = 30
            // 텍스트뷰 레이아웃 적용
            dots[i]?.layoutParams = params
            // 레이아웃에 텍스트뷰 적용
            mainbinding.dotsLayout.addView(dots[i])
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

    // 툴바 사용 설정
    private fun setToolbar(){
        setSupportActionBar(toolbar)

        // 툴바 왼쪽 버튼 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽 버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_cherryred)  // 왼쪽 버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 타이틀 안보이게 하기
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                drawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 네비게이션 드로어 메뉴 클릭 리스너
    //왼쪽의 메뉴를 클릭했을 때 나타나는 이벤트들 입니다
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){  // 네비게이션 메뉴가 클릭되면 스낵바가 나타난다.
            //qr코드 입장 미구현
            R.id.qrcodeIn->{
                val intent = Intent(this, QRCreateActivity::class.java)
                startActivity(intent)
            }
            //qr상품 검색
            R.id.qrcodeSearch->{
                val intent = Intent(this, QrReaderActivity::class.java)
                startActivity(intent)
            }
            //상품
            R.id.product->{
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
            }
            //결제수단관리
            R.id.payment->{
                val intent = Intent(this, CardActivity::class.java)
                startActivity(intent)
            }
            //이벤트
            R.id.event->{
                val intent = Intent(this, EventListActivity::class.java)
                startActivity(intent)
            }
            //자주묻는질문
            R.id.faq->{
                val intent = Intent(this, FaqActivity::class.java)
                startActivity(intent)
            }
            //로그아웃
            R.id.logout->{
                MyApplication.auth.signOut()
                MyApplication.email = null
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }

    /*
     * 뒤로가기 버튼으로 네비게이션 닫기
     *
     * 네비게이션 드로어가 열려 있을 때 뒤로가기 버튼을 누르면 네비게이션을 닫고,
     * 닫혀 있다면 기존 뒤로가기 버튼으로 작동한다.
     */
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers()
        }else{
            Log.d("뒤로가기 버튼","비 활성화")
            Toast.makeText(baseContext, "한번 더 누르면 종료 될까요", Toast.LENGTH_SHORT).show()
        }
    }
}