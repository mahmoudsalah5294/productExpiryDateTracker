package com.mahmoudsalah.goodsexpirydatetracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudsalah.goodsexpirydatetracker.databinding.CustomProductsLayoutBinding
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel

class ProductsRecyclerAdapter(var items:List<ProductsModel>):RecyclerView.Adapter<ProductsRecyclerAdapter.VH>() {
    class VH(val myView: CustomProductsLayoutBinding):RecyclerView.ViewHolder(myView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = CustomProductsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.myView.expiryDate.text = items[position].itemExpiryDate.toString()
        holder.myView.titleTxt.text = items[position].itemName
        holder.myView.typeTxt.text = items[position].itemType
    }

    override fun getItemCount(): Int  = items.size
}