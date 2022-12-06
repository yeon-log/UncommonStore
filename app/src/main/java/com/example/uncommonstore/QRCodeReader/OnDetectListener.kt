package com.example.uncommonstore.QRCodeReader

interface OnDetectListener {
    fun onDetect(msg : String) // ❶
}