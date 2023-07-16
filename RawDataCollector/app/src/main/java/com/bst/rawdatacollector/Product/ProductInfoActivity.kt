package com.bst.rawdatacollector.Product

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding

class ProductInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val machineErrorItems = listOf("해당 없음", "기계 고장1", "기계 고장2", "기계 고장3")

        val machineErrorAdapter = ArrayAdapter(this, R.layout.item_error_list, machineErrorItems)

        binding.machineErrorDropdown.setAdapter(machineErrorAdapter)

        binding.machineErrorDropdown.setOnItemClickListener { adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

//        Log.d("amount", "${binding.doneAmountEditText.text}")

        //button click event -> submit
        binding.submitBtn.setOnClickListener {
            if (binding.doneAmountEditText.text.toString() == "") {

                submitErrorDialog()
            } else {

                showSubmitDialog()
            }
        }

        //recyclerview에 넣자
//        val productErrorList = listOf("해당 없음","제품 불량1","제품 불량2","제품 불량3")
//        val productErrorAdapter = ArrayAdapter(this, R.layout.item_error_list,productErrorList)
//        binding.productErrorDropdown.setAdapter(productErrorAdapter)
//
//        binding.productErrorDropdown.setOnItemClickListener { adapterView, view, i, l ->
//            val itemSelected = adapterView.getItemAtPosition(i)
//            Toast.makeText(this,"item: $itemSelected", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun showSubmitDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_confirm_product, null)
        val machineErrorText = dialogView.findViewById<TextView>(R.id.machineErrorText)
        val productErrorText = dialogView.findViewById<TextView>(R.id.productErrorText)
        val doneAmount = dialogView.findViewById<TextView>(R.id.doneAmount)

        //TODO 각 정보를 받아올 작업 해야함
        machineErrorText.text = binding.machineErrorDropdown.text
        doneAmount.text ="${binding.doneAmountEditText.text} 개 "

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

    private fun submitErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error Message").setMessage("수량을 입력해 주세요")
        builder.setPositiveButton("확인") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }
}