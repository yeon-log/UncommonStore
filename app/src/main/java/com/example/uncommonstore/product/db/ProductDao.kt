package com.example.uncommonstore.product.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
/*****************************************************
 * @function : ProductDao
 * @author : 정구현
 * @Date : 2022.12.05 생성
 *****************************************************/

@Dao
interface ProductDao {
    //상품 리스트 반환
    @Query("SELECT * FROM product")
    fun getProductList() : List<ProductEntity>

    //상품 등록
    @Insert
    fun insertProduct(product : List<ProductEntity>)

    //리스트 갯수 반환
    @Query("SELECT count(*) FROM product")
    fun getProductCount() : Int

    //prodid에 해당하는 특정 게시물 반환
    @Query("SELECT * FROM product WHERE prodId = :id")
    fun getProductById(id : Int) : ProductEntity

    //상품 검색 반환
    @Query("select * from product where prodName like '%' || :word || '%'")
    fun getProductSearch(word : String) : List<ProductEntity>

    //상품 랜덤 검색
    @Query("SELECT * FROM product ORDER BY random() LIMIT 6")
    fun getProductRandomList() : List<ProductEntity>
}