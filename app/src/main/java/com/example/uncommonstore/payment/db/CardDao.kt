package com.example.uncommonstore.payment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {
    @Insert
    fun insertCard(card: CardEntity)

    @Query("SELECT cardName FROM card")
    fun getCardName(): String

    @Query("SELECT * FROM card")
    fun getCardList(): List<CardEntity>

    @Query("DELETE FROM card WHERE cardName = :cardName")
    fun deleteCard(cardName: String?)
}