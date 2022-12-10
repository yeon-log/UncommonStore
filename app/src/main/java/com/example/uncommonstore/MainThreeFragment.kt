package com.example.uncommonstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/*****************************************************
 * @function : MainThreeFragment
 * @author : 김나형
 * @Date : 2022.12.07 생성
 *****************************************************/
class MainThreeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstaceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main_three_fragment, container, false)
    }
}