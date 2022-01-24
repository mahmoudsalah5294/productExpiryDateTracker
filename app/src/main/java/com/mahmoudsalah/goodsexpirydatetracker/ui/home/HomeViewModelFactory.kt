package com.mahmoudsalah.goodsexpirydatetracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface

class HomeViewModelFactory(val repo:LocalRepoInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}