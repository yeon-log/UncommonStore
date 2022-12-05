package com.example.uncommonstore.product.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    fun getProductList() : List<ProductEntity>

    @Insert
    fun insertProduct(product : ProductEntity)

    @Query("SELECT count(*) FROM ProductEntity")
    fun getProductCount() : Int

}