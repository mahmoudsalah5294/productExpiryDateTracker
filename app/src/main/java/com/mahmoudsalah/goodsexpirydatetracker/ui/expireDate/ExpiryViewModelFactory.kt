package com.mahmoudsalah.goodsexpirydatetracker.ui.expireDate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface

class ExpiryViewModelFactory(val repo: LocalRepoInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpireDateViewModel(repo) as T
    }
}