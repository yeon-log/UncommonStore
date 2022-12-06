package com.example.uncommonstore.payment.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) var cardNo: Int? = null,
    @ColumnInfo(name = "cardPw") val cardPw: Int,
    @ColumnInfo(name = "cardCvc") val cardCvc: Int,
    @ColumnInfo(name = "cardExpiration") val cardExpiration: Int,
    @ColumnInfo(name = "cardNum") val cardNum: String,
    @ColumnInfo(name = "cardName") val cardName: String
)