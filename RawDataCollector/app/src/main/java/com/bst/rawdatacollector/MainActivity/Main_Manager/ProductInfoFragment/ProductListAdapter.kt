package com.bst.rawdatacollector.MainActivity.Main_Manager.ProductInfoFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.Product
import com.bst.rawdatacollector.databinding.ItemProductListBinding

class ProductListAdapter(private val context: Context, private val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>()
{

    private var equipmentClickedListener: ProductClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ItemProductListBinding = ItemProductListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val product: Product = productList[position]
        holder.binding.equipmentInfoLayout.setOnClickListener {
            equipmentClickedListener?.onClicked(product)
        }
    }

    fun setProductClickedListener(equipmentClickedListener: ProductClickedListener)
    {
        this.equipmentClickedListener = equipmentClickedListener
    }

    interface ProductClickedListener
    {
        fun onClicked(product:Product)
    }

    inner class ViewHolder(val binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root)
}