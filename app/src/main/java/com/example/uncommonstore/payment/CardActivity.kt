package com.example.uncommonstore.payment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityCardBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.payment.db.CardDao
import com.example.uncommonstore.payment.db.CardEntity
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator3

class CardActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var cardDao: CardDao
    private var cards: MutableList<CardEntity?> = mutableListOf()
    private lateinit var binding: ActivityCardBinding
    private lateinit var mPager: ViewPager2
    private lateinit var pagerAdapter: FragmentStateAdapter
    private val num_page = 4
    private lateinit var mIndicator: CircleIndicator3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        cardDao = db.CardDao()

        getAllCardList()
        binding.btnPaymentRegister.setOnClickListener {
            val intent = Intent(this, CardAddActivity::class.java)
            startActivity(intent)
        }
        var checkedItemIndex = 0    // 선택된 항목을 저장하는 변수
        binding.btnPaymentTermination.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("삭제할 카드를 선택해주세요")

            // 등록되지 않은 카드(null 이면) 체크하기
            var cnt = 0
            for (i in 0 until cards.size) {
                if (cards[i]?.cardName.isNullOrEmpty()) {
                    cnt++
                }
            }

            // null 이 아닌 카드만 라디오 버튼 보여주기
            // 코틀린 문법 Null 로 된 값을 생성하고 싶을 경우 arrayOfNulls 사용 => 이거 못찾아서 계속 null 체크못했었음 소브렝
            val itemList = arrayOfNulls<String>(cnt)
            for(i in 0 until cnt){
                itemList[i] = cards[i]?.cardName.toString()
            }

            // 항목 클릭 시 이벤트
            builder.setSingleChoiceItems(itemList, checkedItemIndex) { dialog, which ->
                checkedItemIndex = which
            }

            // 확인 버튼 누를 시 db 에서 해당 카드 정보 삭제하고 toast 메시지로 삭제 여부 알리기
            builder.setPositiveButton("확인") { dialog, which ->
                /* val del_item = itemList?.get(checkedItemIndex) */
                //cardDao.deleteCard(itemList?.get(checkedItemIndex))

                // 안드로이드는 데이터베이스 쿼리가 메인스레드를 점유할것을 염려해서
                // 메인스레드가 아닌 다른 스레드에서 쿼리를 실행하라고 에러남!!!
                // 그래서 코루틴으로 삭제하기 => Context Switching 시간도 줄일 수 있음
               val del = itemList?.get(checkedItemIndex)
                CoroutineScope(Dispatchers.IO).launch {
                    db?.CardDao()?.deleteCard(del)
                    println("코루틴으로 삭제..? 됨네까?????? 아오오롥 제발ㄹ요오오ㅗㅠㅠㅠ")
                }
            }
            // 취소 버튼 누를 시는 아무 행동 하지 않음
            builder.setNegativeButton("취소") { _, _ ->
            }
            builder.show()
        }
        pageSetting()
        setToolBar()
    }

    private fun pageSetting() {
        // ViewPager2
        mPager = findViewById(R.id.viewpager)

        // Indicator: 뷰페이저 안에 현재 표시되는 페이지를 알 수 있는 표시자 (모양은 라이브러리 마다 다름)
        mIndicator = findViewById(R.id.indicator)
        mIndicator.setViewPager(mPager)
        mIndicator.createIndicators(num_page, 0)

        // ViewPager 세팅하기
        mPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mPager.currentItem = 1000
        mPager.offscreenPageLimit = 3

        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    mPager.currentItem = position
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mIndicator.animatePageSelected(position % num_page)
            }
        })
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
        mPager.setPageTransformer(ViewPager2.PageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (mPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -myOffset
                } else {
                    page.translationX = myOffset
                }
            } else {
                page.translationY = myOffset
            }
        })
    }

    // 이 부분 부터 툴바 부분 12.08 구영모 추가
    private fun setToolBar() {
        setSupportActionBar(card_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.go_back)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when (item.itemId) {
            android.R.id.home -> { // 메뉴 버튼
                finish()
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    } // 툴바 부분 끝

    // room db에 들어가있는 card 정보 만큼 보여주기
    // 정보가 있다면 카드이름을 넣어줄 것
    private fun getAllCardList() {
        Thread {
            println("after: " + cards.size)
            cards = ArrayList(cardDao.getCardList())
            // 아직 카드등록을 하지 않았을때 기본적으로 null 넣어주기
            for (i in 0 until (num_page - cards.size)) {
                cards.add(null)
            }
            println("after: " + cards.size)
            runOnUiThread {
                // Adapter
                pagerAdapter = MyAdapter(this, num_page, cards)
                mPager.adapter = pagerAdapter
            }
        }.start()
    }
}