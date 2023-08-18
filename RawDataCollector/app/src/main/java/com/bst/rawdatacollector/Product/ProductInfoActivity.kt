package com.bst.rawdatacollector.Product

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.SpinnerInterface.SpinnerArrayLists
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding

class ProductInfoActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityProductInfoBinding
    private lateinit var spinnerLists: SpinnerArrayLists

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        spinnerLists = SpinnerArrayLists()

        //스피너를 세팅해주는 함수
        //Spinner 객체, Context, 스피너에 들어갈 Lists 로 구성
        setSpinnerAdapter(binding.testSpinner, this@ProductInfoActivity, spinnerLists.getTestLists())


        //button click event -> submit
        binding.submitBtn.setOnClickListener {
            if (binding.doneAmountEditText.text.toString() == "")
            {
                submitErrorDialog()
            }
            else
            {
                showSubmitDialog()
            }
        }
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
                Toast.makeText(this@ProductInfoActivity,"선택된 아이템:$selectedItem",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }

    }

    private fun showSubmitDialog()
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_confirm_product, null)
        val machineErrorText = dialogView.findViewById<TextView>(R.id.machineErrorText)
        val productErrorText = dialogView.findViewById<TextView>(R.id.productErrorText)
        val doneAmount = dialogView.findViewById<TextView>(R.id.doneAmount)

        //TODO 각 정보를 받아올 작업 해야함
        machineErrorText.text = binding.testSpinner.selectedItem.toString()
        doneAmount.text = "${binding.doneAmountEditText.text} 개 "

        dialogBuilder.setView(dialogView)


        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun submitErrorDialog()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error Message").setMessage("수량을 입력해 주세요")
        builder.setPositiveButton("확인") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }
}