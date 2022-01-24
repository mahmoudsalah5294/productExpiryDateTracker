package com.mahmoudsalah.goodsexpirydatetracker.ui.expireDate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import kotlinx.coroutines.launch

class ExpireDateViewModel(val repo:LocalRepoInterface) : ViewModel() {

    private val expiryProductMutableLiveData = MutableLiveData<List<ProductsModel>>()
    var expiryProductLiveData:LiveData<List<ProductsModel>> = expiryProductMutableLiveData

    fun getExpiryProducts(){
        viewModelScope.launch{
            val list = repo.getExpiryProduct()
            expiryProductMutableLiveData.value = list
        }

    }
}