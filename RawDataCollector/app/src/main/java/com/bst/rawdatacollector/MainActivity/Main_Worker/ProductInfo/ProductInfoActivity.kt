package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.bst.rawdatacollector.Delegate.VoidStringDelegate
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.DoneAmount.ProductInfoFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.SpinnerInterface.SpinnerArrayLists
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding
import com.google.android.material.tabs.TabLayout

class ProductInfoActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityProductInfoBinding
    private lateinit var spinnerLists: SpinnerArrayLists
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var toolInfoFragment: ToolInfoFragment
    private lateinit var productInfoFragment: ProductInfoFragment
    private lateinit var machineErrorFragment: MachineInfoFragment

    private var doneAmount:String=""


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

        //프래그먼트 생명주기가 늦어서 연결이 안됨 고민해보기
//        productInfoFragment.setDoneAmountChangedCallback(object : VoidStringDelegate
//        {
//            override fun voidStringDelegate(_data: String)
//            {
//                doneAmount = _data
//            }
//        })

        //button click event -> submit
        binding.submitBtn.setOnClickListener {
            showSubmitDialog(doneAmount)
        }
    }


    private fun setTabList(tabLayout: TabLayout, tab1: String, tab2: String, tab3: String)
    {
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab1))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab2))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab3))
    }

    private fun showSubmitDialog(_test:String)
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_confirm_product, null)
        val machineErrorText = dialogView.findViewById<TextView>(R.id.machineErrorText)
        val productErrorText = dialogView.findViewById<TextView>(R.id.productErrorText)
        val doneAmountText = dialogView.findViewById<TextView>(R.id.doneAmount)

        //TODO 각 정보를 받아올 작업 해야함
        //TODO 콜백으로 구현하기
        doneAmountText.text = _test

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