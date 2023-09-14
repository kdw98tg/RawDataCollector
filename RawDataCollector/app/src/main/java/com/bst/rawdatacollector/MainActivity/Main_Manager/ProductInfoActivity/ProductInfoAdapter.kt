package com.bst.rawdatacollector.MainActivity.Main_Manager.ProductInfoActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ProductInfo
import com.bst.rawdatacollector.databinding.ItemProductInfoBinding

class ProductInfoAdapter(private val context: Context, private val productInfoList:ArrayList<ProductInfo>): RecyclerView.Adapter<ProductInfoAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ItemProductInfoBinding = ItemProductInfoBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return productInfoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val productInfo:ProductInfo = ProductInfo()
    }
    inner class ViewHolder(val binding: ItemProductInfoBinding):RecyclerView.ViewHolder(binding.root)

}