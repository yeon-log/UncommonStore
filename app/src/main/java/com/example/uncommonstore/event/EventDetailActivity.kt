package com.example.uncommonstore.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.uncommonstore.MainActivity
import com.example.uncommonstore.QRCodeReader.QrReaderActivity
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityEventDetailBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.event.db.EventEntity
import kotlinx.android.synthetic.main.activity_event_detail.*
/*****************************************************
 * @function : EventDetailActivity
 * @author : 김나형
 * @Date : 2022.12.06 생성
 *****************************************************/
class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        val eventData = intent.getSerializableExtra("event") as EventEntity
        val context = binding.root.context

        // roomDB에 있는 데이터를 layout에 넣어주기
        binding.tvEventTitle.text = eventData.eventName.toString()
        binding.tvEventContent.text = eventData.eventContent.toString()
        Glide.with(context)
            .load(eventData.eventImage2)
            .into(binding.imgEventImage1)
        Glide.with(context)
            .load(eventData.eventImage3)
            .into(binding.imgEventImage2)
        Glide.with(context)
            .load(eventData.eventImage4)
            .into(binding.imgEventImage3)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolBar()
    }
    //이벤트 상세 페이지 툴바 부분 12.08 구영모 추가
    private fun setToolBar(){
        setSupportActionBar(event_detail_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.go_back)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                finish()
                return super.onOptionsItemSelected(item)
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