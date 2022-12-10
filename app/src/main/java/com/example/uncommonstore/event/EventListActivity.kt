package com.example.uncommonstore.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityEventListBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.event.db.EventDao
import com.example.uncommonstore.event.db.EventEntity
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.activity_main.*

/*****************************************************
 * @function : EventListActivity
 * @author : 김나형
 * @Date : 2022.12.05 생성
 *****************************************************/
class EventListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventListBinding

    private lateinit var db : AppDatabase
    private lateinit var eventDao: EventDao
    private lateinit var eventList: ArrayList<EventEntity>
    private lateinit var adapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase.getInstance(this)!!
        //room 테이블 DAO 맞춰서 변경
        eventDao = db.EventDao()
        setToolBar()
        getAllEventList()
    }

    //이벤트 리스트 툴바 부분 12.07 구영모 추가
    private fun setToolBar(){
        setSupportActionBar(event_toolbar)
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

    private fun getAllEventList(){
        Thread{
            eventList = ArrayList(eventDao.getAll())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView(){
        runOnUiThread{
            adapter = ListViewAdapter(eventList)
            binding.listView.adapter = adapter
            binding.listView.layoutManager = LinearLayoutManager(this)
            adapter.setOnItemClickListener(object : ListViewAdapter.OnItemClickListener{
                override fun onItemClick(v: View, event: EventEntity, pos : Int) {
                    Intent(this@EventListActivity, EventDetailActivity::class.java).apply {
                        putExtra("event", event)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }
                }

            })
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllEventList()
    }
}