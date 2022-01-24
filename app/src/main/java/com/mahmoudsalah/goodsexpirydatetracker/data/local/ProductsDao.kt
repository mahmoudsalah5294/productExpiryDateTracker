package com.mahmoudsalah.goodsexpirydatetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productsModel: ProductsModel):Long

    @Query("select * from product_table where itemExpiryDate > :currentDate order by itemExpiryDate asc")
    suspend fun getValidProducts(currentDate:Long):List<ProductsModel>

    @Query("select * from product_table where itemExpiryDate <= :currentDate order by itemExpiryDate asc")
    suspend fun getExpiredProducts(currentDate:Long):List<ProductsModel>
}