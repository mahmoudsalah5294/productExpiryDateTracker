package com.mahmoudsalah.goodsexpirydatetracker.repo

import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import kotlin.random.Random

class FakeRepo(val validProductList:MutableList<ProductsModel>,val expiryProductList:MutableList<ProductsModel>):LocalRepoInterface {
    override suspend fun getValidProduct(): List<ProductsModel> {
         return validProductList
    }

    override suspend fun getExpiryProduct(): List<ProductsModel> {
        return expiryProductList
    }

    override suspend fun insertProduct(product: ProductsModel): Long {
        validProductList.add(product)
        return Random.nextLong(1,100)
    }
}