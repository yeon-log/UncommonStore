package com.example.uncommonstore.event.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
/*****************************************************
 * @function : EventDao
 * @author : 김나형
 * @Date : 2022.12.06 생성
 *****************************************************/
@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll() : List<EventEntity>

    @Insert
    fun insertEvent(event : List<EventEntity>)
}