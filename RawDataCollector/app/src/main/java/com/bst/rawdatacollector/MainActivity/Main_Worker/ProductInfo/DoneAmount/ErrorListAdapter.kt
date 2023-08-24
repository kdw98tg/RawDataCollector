package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.DoneAmount

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.ItemErrorListBinding

class ErrorListAdapter(private val context: Context, private val errorList: ArrayList<ProductError>, private val errorType: ArrayList<String>) :
    RecyclerView.Adapter<ErrorListAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ItemErrorListBinding = ItemErrorListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return errorList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val productError = errorList[position]
        setSpinnerAdapter(holder.binding.machineErrorSpinner, context, errorType)
    }

    private fun setSpinnerAdapter(spinner: Spinner, context: Context, stringLists: ArrayList<String>)
    {
        val spinnerAdapter: CustomSpinnerAdapter = CustomSpinnerAdapter(context, stringLists)
        spinner.adapter = spinnerAdapter//어뎁터 생성
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                //고른 아이템을 반환
                val selectedItem = spinnerAdapter.getItem()
                Toast.makeText(context, "선택된 아이템:$selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }

    }

    inner class ViewHolder(val binding: ItemErrorListBinding) : RecyclerView.ViewHolder(binding.root)

}