package com.mahmoudsalah.goodsexpirydatetracker.data.local


import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel

interface LocalRepoInterface{
    suspend fun getValidProduct():List<ProductsModel>

    suspend fun getExpiryProduct():List<ProductsModel>

    suspend fun insertProduct(product:ProductsModel):Long
}


class LocalRepo(val database:ProductsDao):LocalRepoInterface {
    override suspend fun getValidProduct(): List<ProductsModel> {
        return database.getValidProducts(System.currentTimeMillis())
    }

    override suspend fun getExpiryProduct(): List<ProductsModel> {
        return database.getExpiredProducts(System.currentTimeMillis())
    }

    override suspend fun insertProduct(product:ProductsModel):Long{
        return database.insertProduct(product)
    }
}