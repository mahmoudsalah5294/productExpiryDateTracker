package com.mahmoudsalah.goodsexpirydatetracker

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudsalah.goodsexpirydatetracker.data.local.ProductsDataBase
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)

class RoomDatabaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var productsDataBase: ProductsDataBase
    @Before
    fun initDb(){
        productsDataBase = Room.inMemoryDatabaseBuilder(getApplicationContext<Application>(),ProductsDataBase::class.java)
            .allowMainThreadQueries().build()
    }
    @Test
    fun insertAndGetData() = runBlocking{


        val productsModel = ProductsModel("water","drink", Date(2022,1,15))

        productsDataBase.productsDao().insertProduct(productsModel)

        val product = productsDataBase.productsDao().getValidProducts(Date(2022,0,1).time)

        assertEquals("water",product[0].itemName)

    }
    @After
    fun colseDB() = productsDataBase.close()
}