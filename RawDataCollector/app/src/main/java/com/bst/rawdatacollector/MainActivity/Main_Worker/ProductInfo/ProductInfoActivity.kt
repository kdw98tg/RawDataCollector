package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.SpinnerInterface.SpinnerArrayLists
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding
import com.google.android.material.tabs.TabLayout

class ProductInfoActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityProductInfoBinding
    private lateinit var spinnerLists: SpinnerArrayLists
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var machineErrorFragment: ToolInfoFragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        spinnerLists = SpinnerArrayLists()
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, 3)//fragmentManager, lifecycle, tab 개수
        machineErrorFragment = ToolInfoFragment()

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


        binding.submitBtn.setOnClickListener {
            Toast.makeText(this@ProductInfoActivity, "프래그먼트에서 올라온 값 ${machineErrorFragment.getMachineErrorCount()}", Toast.LENGTH_SHORT).show()
        }


        //스피너를 세팅해주는 함수
        //Spinner 객체, Context, 스피너에 들어갈 Lists 로 구성
        //setSpinnerAdapter(binding.testSpinner, this@ProductInfoActivity, spinnerLists.getTestLists())


        //button click event -> submit
//        binding.submitBtn.setOnClickListener {
//            if (binding.doneAmountEditText.text.toString() == "")
//            {
//                submitErrorDialog()
//            }
//            else
//            {
//                showSubmitDialog()
//            }
//        }
    }


    private fun setTabList(tabLayout: TabLayout, tab1: String, tab2: String, tab3: String)
    {
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab1))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab2))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab3))
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
                Toast.makeText(this@ProductInfoActivity, "선택된 아이템:$selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }

    }

//    private fun showSubmitDialog()
//    {
//        val dialogBuilder = AlertDialog.Builder(this)
//        val inflater = LayoutInflater.from(this)
//        val dialogView = inflater.inflate(R.layout.dialog_confirm_product, null)
//        val machineErrorText = dialogView.findViewById<TextView>(R.id.machineErrorText)
//        val productErrorText = dialogView.findViewById<TextView>(R.id.productErrorText)
//        val doneAmount = dialogView.findViewById<TextView>(R.id.doneAmount)
//
//        //TODO 각 정보를 받아올 작업 해야함
//        machineErrorText.text = binding.testSpinner.selectedItem.toString()
//        doneAmount.text = "${binding.doneAmountEditText.text} 개 "
//
//        dialogBuilder.setView(dialogView)
//
//        dialogBuilder.setNegativeButton("취소") { dialog, which ->
//            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
//            dialog.dismiss()
//        }.setPositiveButton("확인") { dialog, which ->
//            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
//            //통신 해야 함
//        }
//
//        val alertDialog = dialogBuilder.create()
//        alertDialog.show()
//    }

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