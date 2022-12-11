package com.example.uncommonstore.faq
/*****************************************************
 * @function : DataItem(FAQ)
 * @author : 김민석
 * @Date : 2022.12.06 생성
 *****************************************************/
data class DataItem(
    val name : String ="",
    val description : String= "",
    var expand : Boolean = false
)