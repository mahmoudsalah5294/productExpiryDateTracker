package com.mahmoudsalah.goodsexpirydatetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mahmoudsalah.goodsexpirydatetracker.util.TimeConverter
import java.util.*

@Entity(tableName = "product_table")
data class ProductsModel(@PrimaryKey(autoGenerate = false)
                         val itemName:String,
                         val itemType:String,
                         @TypeConverters(TimeConverter::class)
                         val itemExpiryDate: Date?
                         )
