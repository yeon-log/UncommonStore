package com.example.uncommonstore.event.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) var eventId: Int? = null,
    @ColumnInfo(name="eventName") val eventName: String,
    @ColumnInfo(name="eventImage") val eventImage: String,
    @ColumnInfo(name="eventContent") val eventContent: String
    ) : Serializable