package com.example.uncommonstore.payment

import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityCardBinding
import me.relex.circleindicator.CircleIndicator3

class CardActivity : FragmentActivity() {

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

        binding.btnPaymentRegister.setOnClickListener {
            val intent = Intent(this, CardAddActivity::class.java)
            startActivity(intent)
        }

        //ViewPager2
        mPager = findViewById(R.id.viewpager)

        // Adapter
        pagerAdapter = MyAdapter(this, num_page)
        mPager.adapter = pagerAdapter

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
}