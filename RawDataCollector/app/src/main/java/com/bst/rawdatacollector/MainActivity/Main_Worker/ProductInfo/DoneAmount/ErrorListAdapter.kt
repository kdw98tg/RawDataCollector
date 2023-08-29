package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.DoneAmount

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.Delegate.VoidArrayListDelegate
import com.bst.rawdatacollector.Delegate.VoidVoidDelegate
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.ItemErrorListBinding

class ErrorListAdapter(private val context: Context, private val errorList: ArrayList<ProductError>, private val errorType: ArrayList<String>) :
    RecyclerView.Adapter<ErrorListAdapter.ViewHolder>()
{
    private var amountChangedListener: VoidArrayListDelegate? = null

    val list = ArrayList<ProductError>()

    private val spinnerAdapter: CustomSpinnerAdapter = CustomSpinnerAdapter(context, errorType)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ItemErrorListBinding = ItemErrorListBinding.inflate(LayoutInflater.from(context), parent, false)
        setSpinnerAdapter(binding.errorListSpinner, context,errorType)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return errorList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val productError = errorList[position]
        holder.binding.amountText.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
            }

            override fun afterTextChanged(p0: Editable?)
            {
                Log.d("호출됨1", "afterTextChanged: 호출됨")

                if (list.size < errorList.size)
                {
                    for (i in 0 until errorList.size)
                    {
                        val errors = ProductError()
                        list.add(errors)
                    }
                }
                for (i in 0 until list.size)
                {
                    list[i].errorName =spinnerAdapter.getItem()
                    list[i].errorAmount = holder.binding.amountText.text.toString().toInt()
                }
                amountChangedListener?.voidArrayListDelegate(list)
            }
        })

    }

    private fun setSpinnerAdapter(spinner: Spinner, context: Context, stringLists: ArrayList<String>)
    {
        spinner.adapter = spinnerAdapter//어뎁터 부착
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                //고른 아이템을 반환
                val selectedItem = spinnerAdapter.getItem()
                for (i in 0 until list.size)
                {
                    list[i].errorName =spinnerAdapter.getItem()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {
            }
        }

    }

    fun setAmountChangedListener(_amountChangedListener: VoidArrayListDelegate)
    {
        amountChangedListener = _amountChangedListener
    }

    inner class ViewHolder(val binding: ItemErrorListBinding) : RecyclerView.ViewHolder(binding.root)

}