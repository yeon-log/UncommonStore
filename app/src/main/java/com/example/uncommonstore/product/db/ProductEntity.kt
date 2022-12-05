package com.example.uncommonstore.product.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) var prodId: Int? = null,
    @ColumnInfo(name="prodName") val prodName: String,
    @ColumnInfo(name="prodPrice") val prodPrice: String,
    @ColumnInfo(name="prodStock") val prodStock: Int,
    @ColumnInfo(name="prodImage") val prodImage: String,
    @ColumnInfo(name="prodContent") val prodContent: String
    )
