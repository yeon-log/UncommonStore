package com.example.uncommonstore.instruction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityAddCardBinding.inflate
import com.example.uncommonstore.databinding.ActivityInstructionBinding
import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.activity_instruction.*

/*****************************************************
 * @function : InstructionActivity
 * @author : 구영모
 * @Date : 2022.12.09 생성
 *****************************************************/
class InstructionActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstructionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
    }


    //이용방법 툴바 부분
    private fun setToolBar(){
        setSupportActionBar(instruction_toolbar)
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
}