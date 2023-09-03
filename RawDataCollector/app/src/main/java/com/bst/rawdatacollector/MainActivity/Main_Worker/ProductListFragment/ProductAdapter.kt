package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.Producing
import com.bst.rawdatacollector.Delegate.ProductClickListener
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.ItemProducelistBinding

class ProductAdapter(private val context: Context, private val productList: ArrayList<Producing>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()
{
    private var productItemTouchCallback: ProductClickListener? = null

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
        val producing = productList[_position]
        _holder.binding.productNameText.text = producing.productName//제품명
        _holder.binding.productCodeText.text = producing.productCode//품번
        _holder.binding.requestName.text = producing.requestName//생산자 명
        _holder.binding.acceptName.text = producing.acceptName//생산자 명
        _holder.binding.processName.text = producing.process

        if (UserData.getInstance(context).isWorking)//작업중일때만 workingText 표시할거임
        {
            _holder.binding.workingText.visibility = View.VISIBLE
        }
        else
        {
            _holder.binding.workingText.visibility = View.GONE
        }


        _holder.binding.productLayout.setOnClickListener {
            productItemTouchCallback?.productSelected(producing.productName,
                producing.productCode,
                producing.requestName,
                producing.acceptName,
                producing.equipmentCode,
                producing.process)
        }

    }

    fun setProductItemTouchCallback(_productItemTouchCallback: ProductClickListener)
    {
        productItemTouchCallback = _productItemTouchCallback
    }

    class ViewHolder(val binding: ItemProducelistBinding) : RecyclerView.ViewHolder(binding.root)

}
