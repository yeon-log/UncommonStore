package com.example.uncommonstore.QRCodeReader

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.example.uncommonstore.MainActivity
import com.example.uncommonstore.R
import com.example.uncommonstore.databinding.ActivityQrcreateBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.coroutines.Dispatchers.Main

class QRCreateActivity : AppCompatActivity() {
    lateinit var qrcreateBinding: ActivityQrcreateBinding
    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }
    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrcreateBinding=ActivityQrcreateBinding.inflate(layoutInflater)
        setContentView(qrcreateBinding.root)
        createQRCode()
        startCountDown()
    }

    fun createQRCode(){

        val qrCode = QRCodeWriter()
        val bitMtx = qrCode.encode(
            intent.getStringExtra("msg") ?: "데이터가 존재하지 않습니다",
            BarcodeFormat.QR_CODE,
            350,
            350
        )
        val bitmap: Bitmap = Bitmap.createBitmap(bitMtx.width, bitMtx.height, Bitmap.Config.RGB_565)
        for(i in 0 .. bitMtx.width-1){
            for(j in 0 .. bitMtx.height-1){
                var color = 0
                if(bitMtx.get(i, j)){
                    color = Color.BLACK
                }else{
                    color = Color.WHITE
                }
                bitmap.setPixel(i, j, color)
            }
        }
        qrcreateBinding.qrImage.setImageBitmap(bitmap)
    }

    private fun startCountDown(){
        currentCountDownTimer = createCountDownTimer(10*1000L)
        currentCountDownTimer?.start()

        }

    private fun createCountDownTimer(initialMills:Long) =
        // 1초 마다 호출되도록 함
        object : CountDownTimer(initialMills, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

        }

    // 기본적으로 함수마다 초의 단위를 통일하는게 좋음. 개발할 때 가독성이 좋음
    fun updateRemainTime(remainMillis: Long){
        // 총 남은 초
        val remainSeconds = remainMillis/1000

        // 분만 보여줌, 초만 보여줌
        remainMinutesTextView.text = "%02d:".format(remainSeconds/60)
        remainSecondsTextView.text= "%02d".format(remainSeconds%60)
    }
}





