package com.mahmoudsalah.goodsexpirydatetracker.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import com.mahmoudsalah.goodsexpirydatetracker.repo.FakeRepo
import com.mahmoudsalah.goodsexpirydatetracker.ui.home.HomeViewModel
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
class HomeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //Given new instance homeViewModel
    private lateinit var viewModel: HomeViewModel
    private lateinit var repo:LocalRepoInterface

    @Before
    fun setup(){
        val validProductsList = listOf(
            ProductsModel("orange","drink", Date(2022,0,26)),
            ProductsModel("water","drink", Date(2022,1,15)),
            ProductsModel("milk","drink", Date(2022,3,1))
        )

        val expiryProductsList = listOf(
            ProductsModel("rice","food", Date(2020,0,26)),
            ProductsModel("nexus","mobile", Date(2010,1,15)),
            ProductsModel("cream","skin-care", Date(2017,3,1))
        )

        repo = FakeRepo(validProductsList.toMutableList(),expiryProductsList.toMutableList())
        viewModel = HomeViewModel(repo)
    }

    @Test
    fun getValidProductFromDatabase(){

        //when get products data
        viewModel.getProducts()
        val result = viewModel.productLiveData.getOrAwaitValue()

        //then results not null
        assertNotNull(result)

    }
}