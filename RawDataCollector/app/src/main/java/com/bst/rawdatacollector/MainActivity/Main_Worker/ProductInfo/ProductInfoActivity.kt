package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class ProductInfoActivity : AppCompatActivity(), ProductInfoFragment.DoneAmountChangedListener, ProductInfoFragment.ProductErrorListChangedListener,
                            MachineInfoFragment.MachineErrorChangedListener, MachineInfoFragment.MachineStoppedTimeChangedListener,
                            MachineInfoFragment.MachineRestartTimeChangedListener, MachineInfoFragment.MachineStoppedTimeAmountChangedListener
{
    private lateinit var binding: ActivityProductInfoBinding
    private lateinit var spinnerLists: SpinnerArrayLists
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var toolInfoFragment: ToolInfoFragment
    private lateinit var productInfoFragment: ProductInfoFragment
    private lateinit var machineErrorFragment: MachineInfoFragment

    private lateinit var errorLists: ArrayList<ProductError>

    private var machineTimeAmount: String = ""
    private var doneAmount: String = "0"
    private var machineErrorType: String = ""
    private var machineStoppedTime: String = ""
    private var machineRestartTime: String = ""

    private val client: OkHttpClient = OkHttpClient()

    companion object
    {
        private const val INSERT_DONE_AMOUNT_URL = "http://kdw98tg.dothome.co.kr/RDC/Update_DoneAmount.php"
        private const val INSERT_PRODUCT_ERROR_URL = "http://kdw98tg.dothome.co.kr/RDC/Insert_ProductError.php"
    }

    //DoneAmountListener의 함수 재정의
    override fun onAmountChanged(doneAmount: String)
    {
        //프래그먼트에서 보낸 매시지
        this.doneAmount = doneAmount
    }

    //ErrorListsListener 의 함수 재정의
    override fun onListChanged(errorList: ArrayList<ProductError>)
    {
        errorLists = errorList
    }

    //MachineErrorTypeListener
    override fun onMachineErrorTypeChanged(errorType: String)
    {
        machineErrorType = errorType
    }

    override fun onMachineStoppedTimeChanged(machineStoppedTime: String)
    {
        this.machineStoppedTime = machineStoppedTime
    }

    override fun onMachineRestartTimeChanged(machineRestartTime: String)
    {
        this.machineRestartTime = machineRestartTime
    }

    override fun onMachineStoppedTimeAmountChanged(machineTimeAmount: String)
    {
        this.machineTimeAmount = machineTimeAmount
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        spinnerLists = SpinnerArrayLists()
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, 2)//fragmentManager, lifecycle, tab 개수
        machineErrorFragment = MachineInfoFragment()
        toolInfoFragment = ToolInfoFragment()
        productInfoFragment = ProductInfoFragment()
        errorLists = ArrayList()


        //일 시작할건지 물어보는 Dialog
        //아니요 누르면 finish()
        //예 누르면 db에 시작 시간 입력
        if (!UserData.getInstance(this@ProductInfoActivity).startWork)
        {
            workStartDialog()
        }


        //tabList 설정
        setTabList(binding.tabLayout, "제품 수량", "기계 불량")

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
            binding.workEndText.text = getCurTime()
            if (doneAmount == "0")
            {
                submitErrorDialog()
            }
            else
            {
                showSubmitDialog()
            }
        }

    }

    private fun setTabList(tabLayout: TabLayout, tab1: String, tab2: String)
    {
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab1))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab2))
        //tabLayout.addTab(binding.tabLayout.newTab().setText(tab3))
    }

    @SuppressLint("SetTextI18n")
    private fun showSubmitDialog()
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)

        //dialogView Init
        val dialogView = inflater.inflate(R.layout.dialog_confirm_product, null)
        val machineErrorText = dialogView.findViewById<TextView>(R.id.machineErrorText)
        val productErrorRecyclerView = dialogView.findViewById<RecyclerView>(R.id.productErrorRecyclerView)
        val acceptUserNameText = dialogView.findViewById<TextView>(R.id.acceptUserNameText)
        val doneAmountText = dialogView.findViewById<TextView>(R.id.doneAmount)
        val machineStoppedTimeText = dialogView.findViewById<TextView>(R.id.machineStoppedTime)
        val machineRestartTimeText = dialogView.findViewById<TextView>(R.id.machineRestartTime)
        val timeAmountText = dialogView.findViewById<TextView>(R.id.machineTimeAmountText)
        val workStartTimeText = dialogView.findViewById<TextView>(R.id.workStartText)
        val workEndText = dialogView.findViewById<TextView>(R.id.workEndText)

        //정보 view에 세팅
        doneAmountText.text = "$doneAmount 개"
        acceptUserNameText.text = UserData.getInstance(this@ProductInfoActivity).userName
        setRecyclerViewAdapter(productErrorRecyclerView, errorLists)
        machineErrorText.text = machineErrorType
        machineStoppedTimeText.text = machineStoppedTime
        machineRestartTimeText.text = machineRestartTime
        timeAmountText.text = machineTimeAmount
        workStartTimeText.text = binding.workStartText.text
        workEndText.text = binding.workEndText.text

        dialogBuilder.setView(dialogView)

        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
            //통신 해야 함
            updateDoneAmount(UserData.getInstance(this@ProductInfoActivity).userCode, doneAmount, "2023-09-02", "123-456")
            for (i in 0 until errorLists.size)
            {

                insertProductError(UserData.getInstance(this@ProductInfoActivity).userCode,
                    "123-456",
                    errorLists[i].errorName,
                    errorLists[i].errorAmount.toString())
            }
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun setRecyclerViewAdapter(_recyclerView: RecyclerView, _errorList: ArrayList<ProductError>)
    {
        val adapter = SubmitErrorAdapter(this@ProductInfoActivity, _errorList)
        _recyclerView.adapter = adapter
        _recyclerView.layoutManager = LinearLayoutManager(this@ProductInfoActivity)
    }

    private fun workStartDialog()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("작업 시작").setMessage("작업을 시작 하시겠습니까?")
        builder.setPositiveButton("네") { dialogInterface, i ->
            //UserData.getInstance(this@ProductInfoActivity).startWork = true
            binding.workStartText.text = getCurTime()
        }
        builder.setNegativeButton("아니요") { dialogInterface, i ->
            //아니요 누르면 다시 돌아감
            finish()
        }
        val dialog = builder.create()
        dialog.show()
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

    @SuppressLint("SimpleDateFormat")
    private fun getCurTime(): String
    {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("kk시 mm분")
        return dateFormat.format(date)
    }

    private fun updateDoneAmount(acceptUser: String, doneAmount: String, workDate: String, productCode: String)
    {
        val body: FormBody =
            FormBody.Builder().add("acceptUser", acceptUser).add("doneAmount", doneAmount).add("workDate", workDate).add("productCode", productCode)
                .build()
        val request = Request.Builder().url(INSERT_DONE_AMOUNT_URL).post(body).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                runOnUiThread {

                    Toast.makeText(applicationContext, "doneAmount 전송 안됨", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    runOnUiThread {

                        Toast.makeText(applicationContext, "doneAmount 성공", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun insertProductError(reportUser: String, productCode: String, errorType: String, amount: String)
    {
        val body: FormBody =
            FormBody.Builder().add("reportUser", reportUser).add("productCode", productCode).add("errorType", errorType).add("amount", amount).build()
        val request = Request.Builder().url(INSERT_PRODUCT_ERROR_URL).post(body).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                runOnUiThread {

                    Toast.makeText(applicationContext, "errors 전송 안됨", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response)
            {
                runOnUiThread {
                    Toast.makeText(applicationContext, "errors 성공", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}