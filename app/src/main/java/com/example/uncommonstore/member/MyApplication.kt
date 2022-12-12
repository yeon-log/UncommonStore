package com.example.uncommonstore.member

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
/*****************************************************
 * @function : MyApplication
 * @author : 구영모
 * @Date : 2022.12.04
 *****************************************************/
class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        var email: String? = null //회원 이메일
        var name: String? = null //회원 이름
        fun checkAuth(): Boolean { //회원의 권한이 생성되었는지 확인하는 메소드
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email //회원 이메일
                name = currentUser.displayName //회원 이름
                currentUser.isEmailVerified //이메일이 인증되었는지 확인
            } ?: let {
                false
            }
        }
    }
    override fun onCreate() {
        super.onCreate() //어플이 시작되었을 때
        auth = Firebase.auth //계정 인증을 위한 auth객체 생성
    }
}

