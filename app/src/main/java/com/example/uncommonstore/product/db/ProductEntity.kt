package com.example.uncommonstore.product.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
/*****************************************************
 * @function : ProductEntity
 * @author : 정구현
 * @Date : 2022.12.05 생성
 *****************************************************/

@Entity(tableName="product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) var prodId: Int? = null,
    @ColumnInfo(name="prodName") val prodName: String,
    @ColumnInfo(name="prodPrice") val prodPrice: String,
    @ColumnInfo(name="prodStock") val prodStock: Int,
    @ColumnInfo(name="prodThumbnail") val prodThumbnail: String,
    @ColumnInfo(name="prodImage") val prodImage: String,
    @ColumnInfo(name="prodContent") val prodContent: String
    ) : Serializable
