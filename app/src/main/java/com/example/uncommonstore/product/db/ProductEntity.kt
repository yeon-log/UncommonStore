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
    @PrimaryKey(autoGenerate = true) var prodId: Int? = null, // 상품 아이디
    @ColumnInfo(name="prodName") val prodName: String,  // 상품 이름
    @ColumnInfo(name="prodPrice") val prodPrice: String,    //상품 가격
    @ColumnInfo(name="prodStock") val prodStock: Int,   //남은 수량
    @ColumnInfo(name="prodThumbnail") val prodThumbnail: String,    //썸네일 이미지
    @ColumnInfo(name="prodImage") val prodImage: String,    //제품설명 이미지
    @ColumnInfo(name="prodContent") val prodContent: String //제품 설명
    ) : Serializable    //객체 전송을 위한 직렬화
