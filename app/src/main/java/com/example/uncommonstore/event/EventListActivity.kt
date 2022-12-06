package com.example.uncommonstore.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uncommonstore.databinding.ActivityEventListBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.event.db.EventDao
import com.example.uncommonstore.event.db.EventEntity
import com.example.uncommonstore.product.ProductRecyclerViewAdapter

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
        getAllEventList()
    }

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
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllEventList()
    }
}