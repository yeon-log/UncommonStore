package com.example.uncommonstore.QRCodeReader

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.uncommonstore.databinding.ActivityQrReaderBinding
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
/*****************************************************
 * @function : QrReaderActivity(QR리더기)
 * @author : 김민석
 * @Date : 2022.12.07 생성
 *****************************************************/
class QrReaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrReaderBinding   //바인딩 변수 생성
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    private val PERMISSIONS_REQUEST_CODE = 1
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)
    //카메라 권한 지정

    private var isDetected= false // ❶

    override fun onResume() {
        super.onResume()
        isDetected= false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰 바인딩 설정을 합니다.
        binding = ActivityQrReaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (!hasPermissions(this)) {
            // 카메라 권한을 요청합니다.
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        } else {
            // 만약 이미 권한이 있다면 카메라를 시작합니다.
            startCamera()
        }
    }

    //권한 유무 확인
    fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    //권한 요청 콜백 함수
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // requestPermissions의 인수로 넣은 PERMISSIONS_REQUEST_CODE와 맞는지 확인을 합니다.
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                Toast.makeText(this@QrReaderActivity, "권한 요청이 승인되었습니다.", Toast.LENGTH_LONG).show()
                startCamera()
            } else {
                Toast.makeText(this@QrReaderActivity, "권한 요청이 거부되었습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    // 미리보기와 이미지 분석을 시작합니다.
    fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            val preview = getPreview()
            val imageAnalysis = getImageAnalysis()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.bindToLifecycle(this, cameraSelector,preview, imageAnalysis)

        }, ContextCompat.getMainExecutor(this))
    }


    //미리보기 객체를 반환합니다.
    fun getPreview(): Preview {
        val preview : Preview = Preview.Builder().build()
        preview.setSurfaceProvider(binding.barcodePreview.getSurfaceProvider())

        return preview
    }

    fun getImageAnalysis(): ImageAnalysis {

        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        val imageAnalysis = ImageAnalysis.Builder().build()

        //Analyzer를 설정합니다.
        imageAnalysis.setAnalyzer(cameraExecutor, QRCodeAnalyzer(object : OnDetectListener {
            override fun onDetect(msg: String) {
                if (!isDetected) { // ❸
                    isDetected = true // 데이터가 감지가 되었으므로 true로 바꾸어줍니다.
                    val intent = Intent(this@QrReaderActivity, QrReaderResultActivity::class.java)
                    intent.putExtra("msg", msg)
                    startActivity(intent)
                }
            }
        }))

        return imageAnalysis
    }

}