package com.example.uncommonstore.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.uncommonstore.databinding.MainNavheaderBinding
import com.example.uncommonstore.member.MyApplication


class MainNavHeaderActivity : AppCompatActivity() {
    lateinit var binding: MainNavheaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainNavheaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.accountTextView.text = "aaa님 반갑습니다."
        setContentView(binding.root)
    }
}