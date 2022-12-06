package com.example.uncommonstore.member

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uncommonstore.MainActivity
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(MyApplication.checkAuth()){
            changeVisibility("login")
        }else {
            changeVisibility("logout")
        }

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리...........................
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            MyApplication.email = account.email
                            changeVisibility("login")
                        }else {
                            changeVisibility("logout")
                        }
                    }
            }catch (e: ApiException){
                changeVisibility("logout")
            }
        }

        binding.logoutBtn.setOnClickListener {
            //로그아웃...........
            MyApplication.auth.signOut()
            MyApplication.email = null
            changeVisibility("logout")
        }

        binding.goSignInBtn.setOnClickListener{
            changeVisibility("signin")
        }

        binding.googleLoginBtn.setOnClickListener {
            //구글 로그인....................
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }
        //회원 가입의 가입 버튼을 눌렀을 때의 메소드
        binding.signBtn.setOnClickListener {
            //이메일,비밀번호 회원가입........................
            val email = binding.authEmailEditView.text.toString()//이메일
            val password = binding.authPasswordEditView.text.toString()//비밀번호
            val passwordCheck = binding.authPasswordCheckEditView.text.toString()//비밀번호 확인

            Log.d("logintest","email:$email, password:$password, passwordCheck:$password")
            //아무것도 입력하지 않았을 때
            if(email==""&&password==""){
                Toast.makeText(baseContext, "정보 입력이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
            else if(password!=passwordCheck){
                Toast.makeText(baseContext, "비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT).show()
                binding.authPasswordCheckEditView.text.clear()
            }
            else{
                MyApplication.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.authEmailEditView.text.clear()
                        binding.authPasswordEditView.text.clear()
                        binding.authPasswordCheckEditView.text.clear()
                        if (task.isSuccessful) {
                            MyApplication.auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    if (sendTask.isSuccessful) {
                                        Toast.makeText(
                                            baseContext, "회원가입에서 성공, 전송된 메일을 확인해 주세요",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        changeVisibility("logout")
                                    } else {
                                        Toast.makeText(baseContext, "메일 발송 실패", Toast.LENGTH_SHORT)
                                            .show()
                                        changeVisibility("logout")
                                    }
                                }
                        } else {
                            Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                            changeVisibility("logout")
                        }
                    }
            }
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            Log.d("logintest","email:$email, password:$password")
            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){
                        if(MyApplication.checkAuth()){
                            MyApplication.email = email
                            changeVisibility("login")
                        }else {
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()

                        }
                    }else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        //회원 가입에서 취소 버튼을 눌렀을때 로그인 화면으로 이동
        binding.signCancel.setOnClickListener{
            binding.run {
                logoutBtn.visibility = View.GONE
                logoImage.visibility = View.VISIBLE
                goSignInBtn.visibility = View.VISIBLE
                googleLoginBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                authPasswordCheckEditView.visibility = View.GONE
                socialLoginTextView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
                signInBtns.visibility = View.GONE
                signInTextView.visibility = View.GONE
            }
        }

    }
//    authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
    fun changeVisibility(mode: String){
        if(mode === "login"){//로그인 하였을때 작동 추후에 작성
            //로그인 하였을 때 메인 액티비티로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            binding.run {
//                logoImage.visibility= View.GONE//로고 이미지 비활성화
//                logoutBtn.visibility= View.VISIBLE//로그아웃 버튼 비활성화
//                goSignInBtn.visibility= View.GONE
//                googleLoginBtn.visibility= View.GONE
//                authEmailEditView.visibility= View.GONE
//                authPasswordEditView.visibility= View.GONE
//                authPasswordCheckEditView.visibility = View.GONE
//                signBtn.visibility= View.GONE
//                loginBtn.visibility= View.GONE
//                signInBtns.visibility = View.GONE
//            }
        }else if(mode === "logout"){//로그아웃 했을때 -> 로그인 화면
            binding.run {
//                authMainTextView.text = "로그인 화면 테스트"
                logoutBtn.visibility = View.GONE
                logoImage.visibility = View.VISIBLE
                goSignInBtn.visibility = View.VISIBLE
                googleLoginBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                authPasswordCheckEditView.visibility = View.GONE
                socialLoginTextView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
                signInBtns.visibility = View.GONE
                signInTextView.visibility = View.GONE
            }
        }else if(mode === "signin"){//회원가입 화면
            binding.run {
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.GONE
                googleLoginBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                authPasswordCheckEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
                logoImage.visibility = View.GONE
                socialLoginTextView.visibility = View.GONE
                signInBtns.visibility = View.VISIBLE
                signInTextView.visibility = View.VISIBLE
            }
        }
    }
}