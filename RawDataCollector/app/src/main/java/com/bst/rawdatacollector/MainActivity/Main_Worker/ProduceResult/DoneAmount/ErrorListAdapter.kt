package com.bst.rawdatacollector.MainActivity.Main_Worker.ProduceResult.DoneAmount

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.Delegate.VoidArrayListDelegate
import com.bst.rawdatacollector.Utils.Spinner.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.ItemErrorListBinding

class ErrorListAdapter(private val context: Context, private val errorList: ArrayList<ProductError>, private val errorType: ArrayList<String>) :
    RecyclerView.Adapter<ErrorListAdapter.ViewHolder>()
{
    private var amountChangedListener: VoidArrayListDelegate? = null

    private val spinnerAdapter: CustomSpinnerAdapter = CustomSpinnerAdapter(context, errorType)

    private lateinit var binding: ItemErrorListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        binding = ItemErrorListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return errorList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int)
    {
        val productError = errorList[position]

        //불량 갯수 EditText textChange 이벤트 관리
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
                //errorList 의 위치에 있는 값을 받아서 리스트에 저장
                //리스너로 erroList를 Activity 로 전달
                productError.errorAmount = holder.binding.amountText.text.toString().toInt()
                amountChangedListener?.voidArrayListDelegate(errorList)
            }
        })

        setSpinnerAdapter(binding.errorListSpinner, position)

    }

    private fun setSpinnerAdapter(spinner: Spinner,  position: Int)
    {
        spinner.adapter = spinnerAdapter//어뎁터 부착
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                //해당 에러리스트의 항목을 가져와서 넘겨줌
                errorList[position].errorName = spinnerAdapter.getItem()
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