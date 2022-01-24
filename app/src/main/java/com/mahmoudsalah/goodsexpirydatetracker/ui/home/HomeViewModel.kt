package com.mahmoudsalah.goodsexpirydatetracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import kotlinx.coroutines.launch

class HomeViewModel(val repo:LocalRepoInterface) : ViewModel() {


    private val productMutableLiveData = MutableLiveData<List<ProductsModel>>()

    var productLiveData:LiveData<List<ProductsModel>> = productMutableLiveData

    fun getProducts(){
        viewModelScope.launch{
            val list = repo.getValidProduct()
            productMutableLiveData.value = list
        }

    }
}