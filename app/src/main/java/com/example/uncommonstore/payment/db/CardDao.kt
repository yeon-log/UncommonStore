package com.example.uncommonstore.payment.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CardDao {
    @Insert
    fun insertCard(card : CardEntity)
}