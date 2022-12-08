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

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        val eventData = intent.getSerializableExtra("event") as EventEntity
        val context = binding.root.context

        println("제목"+eventData.eventName.toString())
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
    //이 부분 부터 툴바 부분
    private fun setToolBar(){
        setSupportActionBar(event_detail_toolbar)
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