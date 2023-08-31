package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.DoneAmount.ProductInfoFragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.Machine.MachineInfoFragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.Tools.ToolInfoFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.SpinnerInterface.SpinnerArrayLists
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding
import com.google.android.material.tabs.TabLayout

class ProductInfoActivity : AppCompatActivity(), ProductInfoFragment.DoneAmountChangedListener, ProductInfoFragment.ProductErrorListChangedListener
{
    private lateinit var binding: ActivityProductInfoBinding
    private lateinit var spinnerLists: SpinnerArrayLists
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var toolInfoFragment: ToolInfoFragment
    private lateinit var productInfoFragment: ProductInfoFragment
    private lateinit var machineErrorFragment: MachineInfoFragment

    private lateinit var errorLists:ArrayList<ProductError>

    private var doneAmount: String = "0"

    //DoneAmountListener의 함수 재정의
    override fun onChanged(doneAmount: String)
    {
        //프래그먼트에서 보낸 매시지
        this.doneAmount = doneAmount
    }
    //ErrorListsListener 의 함수 재정의
    override fun onChanged(errorList: ArrayList<ProductError>)
    {
        errorLists = errorList
        Log.d("호출됨3", "afterTextChanged: 호출됨")
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        spinnerLists = SpinnerArrayLists()
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, 3)//fragmentManager, lifecycle, tab 개수
        machineErrorFragment = MachineInfoFragment()
        toolInfoFragment = ToolInfoFragment()
        productInfoFragment = ProductInfoFragment()
        errorLists = ArrayList()

        //tabList 설정
        setTabList(binding.tabLayout, "제품 수량", "도구 정보", "기계 불량")

        //viewPager 어뎁터 설정
        binding.viewPager.adapter = viewPagerAdapter


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                if (tab != null)
                {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {
            }

            override fun onTabReselected(tab: TabLayout.Tab?)
            {
            }
        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int)
            {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        //button click event -> submit
        binding.submitBtn.setOnClickListener {

            showSubmitDialog()
        }

    }

    private fun setTabList(tabLayout: TabLayout, tab1: String, tab2: String, tab3: String)
    {
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab1))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab2))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab3))
    }

    @SuppressLint("SetTextI18n")
    private fun showSubmitDialog()
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)

        //dialogView Init
        val dialogView = inflater.inflate(R.layout.dialog_confirm_product, null)
        val machineErrorRecyclerView = dialogView.findViewById<RecyclerView>(R.id.productErrorRecyclerView)
        val productErrorRecyclerView = dialogView.findViewById<RecyclerView>(R.id.machineErrorRecyclerView)
        val acceptUserNameText = dialogView.findViewById<TextView>(R.id.acceptUserNameText)
        val doneAmountText = dialogView.findViewById<TextView>(R.id.doneAmount)

        //TODO 각 정보를 받아올 작업 해야함
        //TODO 콜백으로 구현하기
        doneAmountText.text = "$doneAmount 개"
        acceptUserNameText.text = UserData.getInstance(this@ProductInfoActivity).getUserName()
        setRecyclerViewAdapter(productErrorRecyclerView, errorLists)

        dialogBuilder.setView(dialogView)

        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
            //통신 해야 함
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun setRecyclerViewAdapter(_recyclerView:RecyclerView, _errorList:ArrayList<ProductError>)
    {
        val adapter = SubmitErrorAdapter(this@ProductInfoActivity, _errorList)
        _recyclerView.adapter = adapter
        _recyclerView.layoutManager = LinearLayoutManager(this@ProductInfoActivity)
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