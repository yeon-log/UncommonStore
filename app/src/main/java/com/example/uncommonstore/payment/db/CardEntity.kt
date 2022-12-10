package com.example.uncommonstore.payment.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*****************************************************
 * @function : CardEntity
 * @author : 심지연
 * @Date : 2022.12.05 생성
 *****************************************************/

@Entity(tableName = "card")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) var cardNo: Int? = null, // 카드순서 pk
    @ColumnInfo(name = "cardPw") val cardPw: Int, // 카드 비밀번호
    @ColumnInfo(name = "cardCvc") val cardCvc: Int, // 카드 cvc 번호
    @ColumnInfo(name = "cardExpiration") val cardExpiration: Int, // 카드 유효기간
    @ColumnInfo(name = "cardNum") val cardNum: String, // 카드번호
    @ColumnInfo(name = "cardName") var cardName: String // 카드이름
)