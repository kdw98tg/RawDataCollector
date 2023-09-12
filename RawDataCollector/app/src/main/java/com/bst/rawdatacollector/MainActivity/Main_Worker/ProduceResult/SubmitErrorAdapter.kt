package com.bst.rawdatacollector.MainActivity.Main_Worker.ProduceResult

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.databinding.ItemSubmitErrorsBinding

class SubmitErrorAdapter(private val context: Context,
                         private val errorList:ArrayList<ProductError>): RecyclerView.Adapter<SubmitErrorAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ItemSubmitErrorsBinding = ItemSubmitErrorsBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return errorList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val productError:ProductError = errorList[position]
        holder.binding.errorName.text = productError.errorName
        holder.binding.errorAmount.text = productError.errorAmount.toString() + "개"

    }
    inner class ViewHolder(val binding: ItemSubmitErrorsBinding):RecyclerView.ViewHolder(binding.root)
}