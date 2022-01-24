package com.mahmoudsalah.goodsexpirydatetracker.repo

import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*


class RepoTest {
    //given
    private lateinit var repo:LocalRepoInterface
    @Before
    fun setup(){

        val validProductsList = listOf(ProductsModel("orange","drink", Date(2022,0,26)),
            ProductsModel("water","drink",Date(2022,1,15)),
            ProductsModel("milk","drink",Date(2022,3,1)))

        val expiryProductsList = listOf(ProductsModel("rice","food", Date(2020,0,26)),
            ProductsModel("nexus","mobile",Date(2010,1,15)),
            ProductsModel("cream","skin-care",Date(2017,3,1)))

        repo = FakeRepo(validProductsList.toMutableList(),expiryProductsList.toMutableList())
    }
    @Test
    fun getValidProductList() = runTest{
        //when
        val result = repo.getValidProduct()

        //then
        assertThat(result.size,`is`(3))
    }



    @Test
    fun getExpiryProductList() = runTest{
        //when
        val result = repo.getExpiryProduct()

        //then
        assertThat(result.size,`is`(3))
    }
}
