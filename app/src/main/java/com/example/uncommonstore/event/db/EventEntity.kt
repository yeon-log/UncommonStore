package com.example.uncommonstore.event.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
/*****************************************************
 * @function : EventEntity
 * @author : 김나형
 * @Date : 2022.12.06 생성
 *****************************************************/
@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) var eventId: Int? = null,
    @ColumnInfo(name="eventName") val eventName: String,
    @ColumnInfo(name="eventImage") val eventImage: String,
    @ColumnInfo(name="eventImage2") val eventImage2: String,
    @ColumnInfo(name="eventImage3") val eventImage3: String,
    @ColumnInfo(name="eventImage4") val eventImage4: String,
    @ColumnInfo(name="eventContent") val eventContent: String
    ) : Serializable