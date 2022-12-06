package com.example.uncommonstore.payment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uncommonstore.databinding.ActivityAddCardBinding
import com.example.uncommonstore.db.AppDatabase
import com.example.uncommonstore.payment.db.CardDao
import com.example.uncommonstore.payment.db.CardEntity

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
}
