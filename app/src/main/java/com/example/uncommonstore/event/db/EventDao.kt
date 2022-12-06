package com.example.uncommonstore.event.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll() : List<EventEntity>

    @Insert
    fun insertEvent(event : List<EventEntity>)
}