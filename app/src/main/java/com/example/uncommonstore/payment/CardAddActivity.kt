package com.example.uncommonstore.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uncommonstore.MainActivity
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityAddCardBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.payment.db.CardDao
import com.example.uncommonstore.payment.db.CardEntity
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_event_detail.*

class CardAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCardBinding
    lateinit var db: AppDatabase
    lateinit var CardDao: CardDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        CardDao = db.CardDao()

        binding.btnCompletion2.setOnClickListener {
            insertCard()
        }
        setToolBar()
    }

    private fun insertCard() {
        val cardPw = binding.edtCardPw.text.toString()
        val cardCvc = binding.edtCardCvc.text.toString()
        val cardExpiration = binding.edtCardExpiration.text.toString()
        val edtCardNum1 = binding.edtCardNum1.text.toString()
        val edtCardNum2 = binding.edtCardNum2.text.toString()
        val edtCardNum3 = binding.edtCardNum3.text.toString()
        val edtCardNum4 = binding.edtCardNum4.text.toString()
        val cardNum = "$edtCardNum1-$edtCardNum2-$edtCardNum3-$edtCardNum4"
        val cardName = binding.edtCardName.text.toString()

        if (cardPw.isBlank() || cardCvc.isBlank()
            || cardExpiration.isBlank() || cardNum.isBlank() || cardName.isBlank()
        ) {
            Toast.makeText(
                this, "모든 항목을 입력해주세요",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Thread {
                CardDao.insertCard(CardEntity(null, cardPw.toInt(), cardCvc.toInt(),
                                    cardExpiration.toInt(), cardNum, cardName))
                runOnUiThread {
                    Toast.makeText(
                        this, "카드가 등록되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }.start()
        }
    }

    //이 부분 부터 툴바 부분
    private fun setToolBar(){
        setSupportActionBar(card_add_toolbar)
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
