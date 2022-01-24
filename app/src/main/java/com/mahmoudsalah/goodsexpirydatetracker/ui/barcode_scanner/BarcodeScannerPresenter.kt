package com.mahmoudsalah.goodsexpirydatetracker.ui.barcode_scanner

import android.content.SharedPreferences
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepo
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.data.local.ProductsDao
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import com.mahmoudsalah.goodsexpirydatetracker.notification.MyAlarm
import java.text.SimpleDateFormat
import java.util.*


interface BarcodeScannerPresenterInterface{
    suspend fun saveProducts(product:List<String>):Long
}
class BarcodeScannerPresenter(database:ProductsDao,val sharedPreferences: SharedPreferences?,val myAlarm: MyAlarm):BarcodeScannerPresenterInterface {
    private var repo:LocalRepoInterface
    private var counter:Int?
    private var active = false
    init {
        repo = LocalRepo(database)
        counter = sharedPreferences?.getInt("counter",-1)
        if (counter != null && counter == -1){
            sharedPreferences?.edit()?.putInt("counter",0)?.apply()
            counter = 0
        }
    }
    override suspend fun saveProducts(productDetails: List<String>):Long {
        return prepareProductData(productDetails)
    }


    private suspend fun prepareProductData(productDetails:List<String>):Long{
        var result = 0L
        if (productDetails.size == 3) {
            val date = stringToDate(productDetails[2])
            val productsModel = ProductsModel(productDetails[0],productDetails[1],date)
            result =  repo.insertProduct(productsModel)
            val done = setCounter(result,counter)
            if (done){
                date?.let { myAlarm.setAlarm(productDetails[0],it.toString(), it) }
            }
        }
        return result
    }

    private fun setTimeDate(expiryTime: Long): Date {


        val currentTime = Calendar.getInstance().time

        currentTime.time += expiryTime

        return currentTime

    }

    private fun stringToDate(date:String):Date?{
        val after24Hour = Calendar.getInstance().time
        after24Hour.time += (24*60*60*1000)
        val date = SimpleDateFormat("dd/MM/yyyy").parse(date)
        if (date.after(after24Hour)){
            var d:Date? = null
            active = true
            when(counter){
                0 -> {d = setTimeDate(6*60*60*1000)}
                1 -> {d = setTimeDate(12*60*60*1000)}
                2 -> {d = setTimeDate(18*60*60*1000)}
                3 -> {d = setTimeDate(24*60*60*1000)}
            }
           return d
        }else{
            return date
        }
        return null
    }
    private fun setCounter(result: Long,count:Int?):Boolean{
        var done = false
        if (result != 0L && active){
            active = false
            count?.let {
                counter = it + 1
                if (counter!! < 4) {
                    sharedPreferences?.edit()?.putInt("counter", counter!!)?.apply()
                }else{
                    counter = 0
                    sharedPreferences?.edit()?.putInt("counter", 0)?.apply()
                }
                done = true
            }
        }
        return done
    }

}