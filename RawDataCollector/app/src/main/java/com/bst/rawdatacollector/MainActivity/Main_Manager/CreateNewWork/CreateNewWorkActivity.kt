package com.bst.rawdatacollector.MainActivity.Main_Manager.CreateNewWork

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.NewWork
import com.bst.rawdatacollector.Utils.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.ActivityCreateNewWorkBinding

class CreateNewWorkActivity : AppCompatActivity()
{

    private lateinit var binding:ActivityCreateNewWorkBinding
    private lateinit var newWorkList:ArrayList<NewWork>
    private lateinit var createNewWorkAdapter:CreateNewWorkAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        newWorkList = ArrayList()
        createNewWorkAdapter= CreateNewWorkAdapter(this@CreateNewWorkActivity,newWorkList)

        setSpinnerAdapter(binding.acceptUserSpinner,this@CreateNewWorkActivity, arrayListOf("1","2"))//작업자
        setSpinnerAdapter(binding.equipmentSpinner,this@CreateNewWorkActivity, arrayListOf("1","2"))//장비
        setSpinnerAdapter(binding.productSpinner,this@CreateNewWorkActivity, arrayListOf("1","2"))//제품
        setSpinnerAdapter(binding.processSpinner,this@CreateNewWorkActivity, arrayListOf("1","2"))//공정

        binding.producingListRecyclerView.adapter = createNewWorkAdapter
        binding.producingListRecyclerView.layoutManager = LinearLayoutManager(this@CreateNewWorkActivity)

        binding.addListBtn.setOnClickListener {
            val newWork=NewWork()
            newWork.requestUser = "1"
            newWork.acceptUser = "2"
            newWork.workDate = "3"
            newWork.equipment = "4"
            newWork.product = "5"
            newWork.amount = "6"
            newWork.process = "7"
            newWorkList.add(newWork)
            createNewWorkAdapter.notifyItemInserted(newWorkList.size)
        }


    }

    private fun setSpinnerAdapter(spinner: Spinner, context: Context, stringLists: ArrayList<String>)
    {
        val spinnerAdapter = CustomSpinnerAdapter(context, stringLists)
        spinner.adapter = spinnerAdapter//어뎁터 생성
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                //고른 아이템을 반환
                val selectedItem = spinnerAdapter.getItem()
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }
    }
}