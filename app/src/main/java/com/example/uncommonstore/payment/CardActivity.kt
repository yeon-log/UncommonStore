package com.example.uncommonstore.payment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityAddCardBinding
import com.example.uncommonstore.databinding.ActivityCardBinding
import com.example.uncommonstore.databinding.ActivityCardFrame1pBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.payment.db.CardDao
import com.example.uncommonstore.payment.db.CardEntity
import com.example.uncommonstore.product.ProductDetailActivity
import com.example.uncommonstore.product.db.ProductEntity
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_event_list.*
import me.relex.circleindicator.CircleIndicator3

class CardActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var CardDao: CardDao
    private lateinit var cards : List<CardEntity>

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
        CardDao = db.CardDao()

        getAllCardList()
        binding.btnPaymentRegister.setOnClickListener {
            val intent = Intent(this, CardAddActivity::class.java)
            startActivity(intent)
        }

        var checkedItemIndex = 0    // 선택된 항목을 저장하는 변수
        binding.btnPaymentTermination.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("삭제할 카드를 선택해주세요")
            val itemList = arrayOf<String>("카드1", "카드2") // 항목 리스트

            // 항목 클릭 시 이벤트
            builder.setSingleChoiceItems(itemList, checkedItemIndex) { dialog, which ->
                checkedItemIndex = which
            }

            // 긍정 버튼
            builder.setPositiveButton("확인") { dialog, which ->
                binding.btnPaymentTermination.text = "선택된 항목: ${itemList[checkedItemIndex]}"
            }
            // 부정 버튼
            builder.setNegativeButton("취소") { dialog, which ->
                binding.btnPaymentTermination.text = "취소되었습니다"
                Toast.makeText(baseContext, "취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        //ViewPager2
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
        setToolBar()
    }

    //이 부분 부터 툴바 부분 12.08 구영모 추가
    private fun setToolBar(){
        setSupportActionBar(card_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.go_back)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                finish()
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //툴바 부분 끝


    private fun getAllCardList(){
        Thread{
            cards = ArrayList(CardDao.getCardList())

            runOnUiThread {
                // Adapter
                pagerAdapter = MyAdapter(this, num_page, cards!!)
                mPager.adapter = pagerAdapter

            }

        }.start()
    }
    private fun getCardName() {
        var binding: ActivityCardFrame1pBinding

    }

}