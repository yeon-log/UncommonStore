package com.example.uncommonstore.product.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getProductList() : List<ProductEntity>

    @Insert
    fun insertProduct(product : List<ProductEntity>)

    @Query("SELECT count(*) FROM product")
    fun getProductCount() : Int

}