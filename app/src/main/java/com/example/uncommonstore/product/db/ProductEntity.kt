package com.example.uncommonstore.product.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity (
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    @ColumnInfo(name="productName") val productName : String,
    @ColumnInfo(name="productPrice") val productPrice : String
)
