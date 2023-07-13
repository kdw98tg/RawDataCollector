package com.bst.rawdatacollector

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.Delegate.VoidVoidDelegate
import com.bst.rawdatacollector.databinding.ItemProducelistBinding

class ProductAdapter(private val context: Context, private val productList: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()
{
    private var productItemTouchCallback: VoidVoidDelegate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = ItemProducelistBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return productList.size
    }

    override fun onBindViewHolder(_holder: ViewHolder, _position: Int)
    {
        val product = productList[_position]
        _holder.binding.productNameText.text = product.productName//제품명
        _holder.binding.productNumberText.text = product.productNumber//품번
        _holder.binding.producerNameText.text = product.producerName//생산자 명

        _holder.binding.productLayout.setOnClickListener{
            productItemTouchCallback?.voidVoidDelegate()
        }

    }
    fun setProductItemTouchCallback(_productItemTouchCallback:VoidVoidDelegate)
    {
        productItemTouchCallback = _productItemTouchCallback
    }

    class ViewHolder(val binding: ItemProducelistBinding) : RecyclerView.ViewHolder(binding.root)

}
