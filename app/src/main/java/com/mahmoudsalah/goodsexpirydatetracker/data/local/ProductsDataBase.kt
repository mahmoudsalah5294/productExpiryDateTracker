package com.mahmoudsalah.goodsexpirydatetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import com.mahmoudsalah.goodsexpirydatetracker.util.TimeConverter

@Database(entities = [ProductsModel::class], version = 1)
@TypeConverters(TimeConverter::class)
abstract class ProductsDataBase:RoomDatabase() {
    abstract fun productsDao():ProductsDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        // @Volatile
        private var INSTANCE: ProductsDataBase? = null

        fun getProductsData(context: Context): ProductsDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext, ProductsDataBase::class.java, "products_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }
    }
}