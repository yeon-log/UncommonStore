package com.example.uncommonstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uncommonstore.databinding.ActivityCardBinding
import com.example.uncommonstore.databinding.ActivityEventListBinding
import com.example.uncommonstore.databinding.ActivityFaqBinding
import com.example.uncommonstore.databinding.ActivityMainBinding
import com.example.uncommonstore.databinding.ActivityProductListBinding
import com.example.uncommonstore.event.EventListActivity
import com.example.uncommonstore.faq.FaqActivity
import com.example.uncommonstore.member.AuthActivity
import com.example.uncommonstore.member.MyApplication
import com.example.uncommonstore.payment.CardActivity
import com.example.uncommonstore.product.ProductListActivity


class MainActivity : AppCompatActivity() {
    lateinit var mainbinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainbinding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainbinding.root)
        mainbinding.accountTextView.text = "${MyApplication.email} 님 반갑습니다."

        mainbinding.logout.setOnClickListener{
            MyApplication.auth.signOut()
            MyApplication.email = null
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
        mainbinding.btnPayment.setOnClickListener{
            val intent = Intent(this, CardActivity::class.java)
            startActivity(intent)
        }
        mainbinding.btnProduct.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }
        mainbinding.btnEvent.setOnClickListener{
            val intent = Intent(this, EventListActivity::class.java)
            startActivity(intent)
        }
        mainbinding.btnFaq.setOnClickListener{
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }
    }

}