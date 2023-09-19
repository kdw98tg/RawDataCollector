package com.bst.rawdatacollector.MainActivity.Main_Worker.ProduceResult

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProduceResult.Product.ProductResultFragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProduceResult.Equipment.EquipmentResultFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.Utils.Utils.Spinners.SpinnerArrayLists
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.ActivityProduceResultBinding
import com.google.android.material.tabs.TabLayout
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class ProduceResultActivity : AppCompatActivity(), ProductResultFragment.DoneAmountChangedListener, ProductResultFragment.ProductErrorListChangedListener,
                              EquipmentResultFragment.EquipmentErrorChangedListener, EquipmentResultFragment.EquipmentStoppedTimeChangedListener,
                              EquipmentResultFragment.EquipmentRestartTimeChangedListener, EquipmentResultFragment.EquipmentStoppedTimeAmountChangedListener
{
    //view 생성
    private lateinit var binding: ActivityProduceResultBinding
    private lateinit var spinnerLists: SpinnerArrayLists
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    //프래그먼트 생성
    private lateinit var productResultFragment: ProductResultFragment
    private lateinit var equipmentResultFragment: EquipmentResultFragment


    //프래그먼트에서 받은 값들을 저장할 변수
    private lateinit var errorLists: ArrayList<ProductError>
    private var equipmentTimeAmount: String = ""
    private var doneAmount: String = "0"
    private var equipmentErrorType: String = ""
    private var equipmentStoppedTime: String = ""
    private var equipmentRestartTime: String = ""
    private var latestDoneAmount:String="0"

    private val client: OkHttpClient = OkHttpClient()

    //작업 정보를 클릭했을때 받을 변수들
    private lateinit var produceNumber:String
    private lateinit var productName: String
    private lateinit var productCode: String
    private lateinit var requestCode: String
    private lateinit var acceptCode: String
    private lateinit var equipmentCode: String
    private lateinit var process: String

    private val userData: UserData = UserData.getInstance(this@ProduceResultActivity)

    companion object
    {
        private const val INSERT_DONE_AMOUNT_URL = "http://kdw98tg.dothome.co.kr/RDC/Update_DoneAmount.php"
        private const val INSERT_PRODUCT_ERROR_URL = "http://kdw98tg.dothome.co.kr/RDC/Insert_ProductError.php"
        private const val INSERT_EQUIPMENT_ERROR_URL = "http://kdw98tg.dothome.co.kr/RDC/Insert_EquipmentError.php"
        private const val UPDATE_WORK_START_TIME_URL = "http://kdw98tg.dothome.co.kr/RDC/Update_WorkStartTime.php"
        private const val UPDATE_WORK_END_TIME_URL = "http://kdw98tg.dothome.co.kr/RDC/Update_WorkEndTime.php"
        private const val SELECT_WORK_START_TIME_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_WorkStartTime.php"
        private const val SELECT_WORK_END_TIME_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_WorkEndTime.php"//그날 기계 수리가 안됐을때 사용할듯
        private const val SELECT_DONE_AMOUNT_URL =
            "http://kdw98tg.dothome.co.kr/RDC/Select_DoneAmount.php/"//처음 DoneAmount 갯수를 가져옴


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
        equipmentErrorType = errorType
    }

    override fun onMachineStoppedTimeChanged(machineStoppedTime: String)
    {
        this.equipmentStoppedTime = machineStoppedTime
    }

    override fun onMachineRestartTimeChanged(machineRestartTime: String)
    {
        this.equipmentRestartTime = machineRestartTime
    }

    override fun onMachineStoppedTimeAmountChanged(machineTimeAmount: String)
    {
        this.equipmentTimeAmount = machineTimeAmount
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProduceResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getIntent
        produceNumber = intent.getStringExtra("produceNumber").toString()
        productName = intent.getStringExtra("productName").toString()
        productCode = intent.getStringExtra("productCode").toString()
        requestCode = intent.getStringExtra("requestCode").toString()
        acceptCode = intent.getStringExtra("acceptCode").toString()
        equipmentCode = intent.getStringExtra("equipmentCode").toString()
        process = intent.getStringExtra("process").toString()

        //init
        spinnerLists = SpinnerArrayLists()
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, 2)//fragmentManager, lifecycle, tab 개수
        equipmentResultFragment = EquipmentResultFragment()
        productResultFragment = ProductResultFragment()
        errorLists = ArrayList()



        Log.d("장비번호", "onCreate: $equipmentCode")

        //들어갔을때 해당 정보로 worktime을 받아오고, 00:00:00 이면 일을 시작하겠냐고 물어봄
        selectWorkStartTime(userData.userCode, getCurDate().toString(), productCode)
        
        selectDoneAmount(produceNumber)//TODO 이거 오류 수정 해야함


        //제일 상단 뷰 세팅 (제품정보)
        binding.productCodeText.text = productCode
        binding.productNameText.text = productName


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
            binding.workEndText.text = getCurTime_24H()
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

    private fun selectDoneAmount(produceNumber: String) {
        val client: OkHttpClient = OkHttpClient()
        val body = FormBody.Builder().add("produceNumber", produceNumber).build()
        val request = Request.Builder().url(SELECT_DONE_AMOUNT_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val result = response.body!!.string()
                    try {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        //JsonObject 해체 작업
                        val json = jsonArray.getJSONObject(0)
                        val doneAmount = json.getString("doneAmount").toString()

                        val bundle = Bundle()
                        bundle.putString("doneAmount",doneAmount)
                        productResultFragment.arguments = bundle

                        //viewPager 어뎁터 설정

                    } catch (e: Exception)//로그인 실패시
                    {
                        e.printStackTrace()
                    }
                }
            }

        })

    }
    private fun setTabList(tabLayout: TabLayout, tab1: String, tab2: String)
    {
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab1))
        tabLayout.addTab(binding.tabLayout.newTab().setText(tab2))
        //tabLayout.addTab(binding.tabLayout.newTab().setText(tab3))
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        setRecyclerViewAdapter(productErrorRecyclerView, errorLists)

        //정보 view에 세팅
        doneAmountText.text = "$doneAmount 개"
        acceptUserNameText.text = userData.userName
        machineErrorText.text = equipmentErrorType
        machineStoppedTimeText.text = equipmentStoppedTime
        machineRestartTimeText.text = equipmentRestartTime
        timeAmountText.text = equipmentTimeAmount
        workStartTimeText.text = binding.workStartText.text
        workEndText.text = binding.workEndText.text

        dialogBuilder.setView(dialogView)

        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->

            userData.isWorking = false
            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()

            val userCode = userData.userCode

            updateDoneAmount(userCode, doneAmount, getCurDate().toString(), productCode)//완료 갯수 전송
            for (i in 0 until errorLists.size)//불량 정보 전송
            {

                insertProductError(userCode, productCode, errorLists[i].errorName, errorLists[i].errorAmount.toString())
            }

            if(equipmentStoppedTime!="")  //기계가 안멈춘걸 의미
            {
                insertEquipmentError(userCode,
                    equipmentCode,
                    equipmentErrorType,
                    getCurDateToDateTimeFormat(equipmentStoppedTime),
                    getCurDateToDateTimeFormat(equipmentRestartTime))//기계 불량 전송
            }

            updateWorkEndTime(userData.userCode, getCurTime(), getCurDate().toString(), productCode)//끝난시간 저장
            finish()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun setRecyclerViewAdapter(_recyclerView: RecyclerView, _errorList: ArrayList<ProductError>)
    {
        val adapter = SubmitErrorAdapter(this@ProduceResultActivity, _errorList)
        _recyclerView.adapter = adapter
        _recyclerView.layoutManager = LinearLayoutManager(this@ProduceResultActivity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun workStartDialog()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("작업 시작").setMessage("작업을 시작 하시겠습니까?")
        builder.setPositiveButton("네") { dialogInterface, i ->
            userData.isWorking = true
            binding.workStartText.text = getCurTime_24H()
            updateWorkStartTime(userData.userCode, getCurTime(), getCurDate().toString(), productCode)
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

    private fun insertEquipmentError(reportUser: String, equipmentCode: String, errorType: String, stoppedTime: String, restartTime: String)
    {
        val body: FormBody = FormBody.Builder().add("reportUser", reportUser).add("equipmentCode", equipmentCode).add("errorType", errorType)
            .add("stoppedTime", stoppedTime).add("restartTime", restartTime).build()

        val request = Request.Builder().url(INSERT_EQUIPMENT_ERROR_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                runOnUiThread {

                    Toast.makeText(applicationContext, "equip 전송 안됨", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response)
            {
                runOnUiThread {
                    Toast.makeText(applicationContext, "equip 성공", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun selectWorkStartTime(acceptUser: String, workDate: String, productCode: String)
    {
        var workStartTime: String? = null
        val body: FormBody = FormBody.Builder().add("acceptUser", acceptUser).add("workDate", workDate).add("productCode", productCode).build()
        val request = Request.Builder().url(SELECT_WORK_START_TIME_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    val result = response.body!!.string()
                    try
                    {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))
                        val json = jsonArray.getJSONObject(0)

                        workStartTime = json.getString("work_start_time").toString()
                        Log.d("시작시간", "onResponse: $workStartTime")
                        if (workStartTime == "00:00:00" && !userData.isWorking)
                        {
                            //일 시작할건지 물어보는 Dialog
                            //아니요 누르면 finish()
                            //예누르면 userData.isWorking이 true가 됨
                            //true 가되면 다른 일은 못함
                            runOnUiThread {
                                workStartDialog()

                            }
                        }
                        else
                        {
                            //작업중이라고, 다른 작업 끝내고 다시 오셈 Dialog
                            runOnUiThread {
                                Toast.makeText(applicationContext, "작업중이거나 오류", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    catch (e: Exception)
                    {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    private fun updateWorkStartTime(acceptUser: String, workStartTime: String, workDate: String, productCode: String)
    {
        val body: FormBody = FormBody.Builder().add("acceptUser", acceptUser).add("workStartTime", workStartTime).add("workDate", workDate)
            .add("productCode", productCode).build()

        val request = Request.Builder().url(UPDATE_WORK_START_TIME_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                runOnUiThread {

                    Toast.makeText(applicationContext, "작업시간 전송 안됨", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response)
            {
                runOnUiThread {
                    Toast.makeText(applicationContext, "작업시간 넣기 성공", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun updateWorkEndTime(acceptUser: String, workEndTime: String, workDate: String, productCode: String)
    {
        val body: FormBody =
            FormBody.Builder().add("acceptUser", acceptUser).add("workEndTime", workEndTime).add("workDate", workDate).add("productCode", productCode)
                .build()

        val request = Request.Builder().url(UPDATE_WORK_END_TIME_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                runOnUiThread {

                    Toast.makeText(applicationContext, "equip 전송 안됨", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response)
            {
                runOnUiThread {
                    Toast.makeText(applicationContext, "equip 성공", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDateToDateTimeFormat(time: String): String
    {
        return LocalDate.now().toString() + " " + time
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurTime_24H(): String
    {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("kk시 mm분")
        return dateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurTime(): String
    {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("kk:mm:ss")
        return dateFormat.format(date)
    }

}