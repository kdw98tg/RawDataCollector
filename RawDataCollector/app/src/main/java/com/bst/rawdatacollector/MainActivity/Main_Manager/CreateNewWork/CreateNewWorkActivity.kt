package com.bst.rawdatacollector.MainActivity.Main_Manager.CreateNewWork

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.NewWork
import com.bst.rawdatacollector.MainActivity.Main_Manager.MainActivityManager
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.Utils.Utils.Spinners.CustomSpinnerAdapter
import com.bst.rawdatacollector.Utils.Utils.Spinners.SpinnerArrayLists
import com.bst.rawdatacollector.Utils.Utils.URL.URLManager
import com.bst.rawdatacollector.databinding.ActivityCreateNewWorkBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate

class CreateNewWorkActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityCreateNewWorkBinding
    private lateinit var newWorkList: ArrayList<NewWork>
    private lateinit var createNewWorkAdapter: CreateNewWorkAdapter

    private lateinit var spinnerArrayLists: SpinnerArrayLists

    companion object
    {
        private const val SELECT_EQUIPMENTS_URL = "${URLManager.PHP_URL}Select_Equipments.php"
        private const val SELECT_WORKERS_URL = "${URLManager.PHP_URL}Select_Workers.php"
        private const val SELECT_PRODUCTS_URL = "${URLManager.PHP_URL}Select_Products.php"
        private const val SELECT_PROCESSES_URL = "${URLManager.PHP_URL}Select_Processes.php"
        private const val INSERT_NEW_WORK_URL = "${URLManager.PHP_URL}Insert_NewWork.php"
        private const val SELECT_TOOLS_URL = "${URLManager.PHP_URL}Select_Tools.php"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        newWorkList = ArrayList()
        createNewWorkAdapter = CreateNewWorkAdapter(this@CreateNewWorkActivity, newWorkList)
        spinnerArrayLists = SpinnerArrayLists()

        selectWorkerList(spinnerArrayLists)//작업자 Spinner 설정
        selectEquipmentsList(spinnerArrayLists)//장비 Spinner 설정
        selectProductList(spinnerArrayLists)//제품 Spinner 설정
        selectProcessList(spinnerArrayLists)//공정 Spinner 설정
        selectToolList(spinnerArrayLists)//공구 Spinner 설정


        binding.producingListRecyclerView.adapter = createNewWorkAdapter
        binding.producingListRecyclerView.layoutManager = LinearLayoutManager(this@CreateNewWorkActivity)

        binding.addListBtn.setOnClickListener {
            if(binding.requestAmountEditText.text.toString() == "")
            {
                errorDialog("작업 수량을 입력해 주세요.")
                return@setOnClickListener
            }
            insertNewWorkToRecyclerView()//기입한 정보를 RecyclerView 에 추가
            clearView()//기입하나 했으면 clear
            Toast.makeText(applicationContext, "추가 되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.submitBtn.setOnClickListener {
            for (i in 0 until newWorkList.size)
            {
                val requestUser = UserData.getInstance(this@CreateNewWorkActivity).userCode
                val acceptUser = newWorkList[i].acceptUser
                val equipment = newWorkList[i].equipment
                val workDate = getCurDate()
                val product = newWorkList[i].product
                val toolCode = newWorkList[i].toolCode
                val amount = newWorkList[i].amount
                val process = newWorkList[i].process
                //RecyclerView 에 있는 정보들을 DB에 업로드 하는 함수
                Log.d("팀원관리", "onCreate: $equipment, ${workDate.toString()}")
                insertNewWork(requestUser, acceptUser, workDate.toString(), equipment, product, toolCode,amount, process)
            }
//            val intent = Intent(this@CreateNewWorkActivity,MainActivityManager::class.java)
//            startActivity(intent)
        }
    }

    private fun selectToolList(spinnerArrayLists: SpinnerArrayLists)
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_TOOLS_URL).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    val result = response.body!!.string()
                    val jsonObject = JSONObject(result)
                    val jsonArray = JSONArray(jsonObject.getString("results"))

                    //JsonObject 해체 작업
                    for (i in 0 until jsonArray.length())
                    {
                        val json = jsonArray.getJSONObject(i)
                        val toolCode = json.getString("tool_code").toString()
                        spinnerArrayLists.toolList.add(toolCode)
                    }
                    runOnUiThread {
                        setSpinnerAdapter(binding.toolSpinner, this@CreateNewWorkActivity, spinnerArrayLists.toolList)//도구
                    }
                }
            }
        })
    }

    private fun selectEquipmentsList(_spinnerArrayLists: SpinnerArrayLists)
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_EQUIPMENTS_URL).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    val result = response.body!!.string()
                    val jsonObject = JSONObject(result)
                    val jsonArray = JSONArray(jsonObject.getString("results"))


                    //JsonObject 해체 작업
                    for (i in 0 until jsonArray.length())
                    {
                        val json = jsonArray.getJSONObject(i)
                        val equipment = json.getString("equipment_code").toString()
                        _spinnerArrayLists.equipmentList.add(equipment)
                    }
                    runOnUiThread {
                        setSpinnerAdapter(binding.equipmentSpinner, this@CreateNewWorkActivity, _spinnerArrayLists.equipmentList)//장비
                    }
                }
            }
        })
    }

    private fun selectWorkerList(_spinnerArrayLists: SpinnerArrayLists)
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_WORKERS_URL).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    val result = response.body!!.string()
                    val jsonObject = JSONObject(result)
                    val jsonArray = JSONArray(jsonObject.getString("results"))


                    //JsonObject 해체 작업
                    for (i in 0 until jsonArray.length())
                    {
                        val json = jsonArray.getJSONObject(i)
                        val worker = json.getString("name").toString()
                        _spinnerArrayLists.workerList.add(worker)
                    }
                    runOnUiThread {
                        setSpinnerAdapter(binding.acceptUserSpinner, this@CreateNewWorkActivity, _spinnerArrayLists.workerList)//작업자
                    }
                }
            }
        })
    }

    private fun selectProductList(_spinnerArrayLists: SpinnerArrayLists)
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_PRODUCTS_URL).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    val result = response.body!!.string()
                    val jsonObject = JSONObject(result)
                    //Log.d("리절트", "onResponse: ${jsonObject}")
                    val jsonArray = JSONArray(jsonObject.getString("results"))

                    //JsonObject 해체 작업
                    for (i in 0 until jsonArray.length())
                    {
                        val json = jsonArray.getJSONObject(i)
                        val product = json.getString("product_name").toString()
                        _spinnerArrayLists.productList.add(product)
                    }
                    runOnUiThread {
                        setSpinnerAdapter(binding.productSpinner, this@CreateNewWorkActivity, _spinnerArrayLists.productList)//제품
                    }
                }
            }
        })
    }

    private fun selectProcessList(_spinnerArrayLists: SpinnerArrayLists)
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_PROCESSES_URL).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    val result = response.body!!.string()
                    val jsonObject = JSONObject(result)
                    val jsonArray = JSONArray(jsonObject.getString("results"))

                    //JsonObject 해체 작업
                    for (i in 0 until jsonArray.length())
                    {
                        val json = jsonArray.getJSONObject(i)
                        val process = json.getString("process").toString()
                        _spinnerArrayLists.processList.add(process)
                    }
                    runOnUiThread {
                        setSpinnerAdapter(binding.processSpinner, this@CreateNewWorkActivity, _spinnerArrayLists.processList)//공정
                    }
                }
            }
        })
    }

    private fun insertNewWork(
        requestUser: String, acceptUser: String, workDate: String, equipment: String, product: String, toolCode:String,amount: String, process: String
    )
    {
        val client: OkHttpClient = OkHttpClient()
        val body =
            FormBody.Builder().add("requestUser", requestUser).add("acceptUser", acceptUser).add("workDate", workDate).add("equipment", equipment).add("toolCode",toolCode)
                .add("product", product).add("amount", amount).add("process", process).build()
        val request = Request.Builder().url(INSERT_NEW_WORK_URL).post(body).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if (response.isSuccessful)
                {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "성공적으로 등록 되었습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertNewWorkToRecyclerView()
    {
        val newWork = NewWork()
        newWork.requestUser = UserData.getInstance(this@CreateNewWorkActivity).userName
        newWork.acceptUser = binding.acceptUserSpinner.selectedItem.toString()
        newWork.equipment = binding.equipmentSpinner.selectedItem.toString()
        newWork.product = binding.productSpinner.selectedItem.toString()
        newWork.toolCode = binding.toolSpinner.selectedItem.toString()
        newWork.amount = binding.requestAmountEditText.text.toString()
        newWork.process = binding.processSpinner.selectedItem.toString()
        newWorkList.add(newWork)
        createNewWorkAdapter.notifyItemInserted(newWorkList.size)
    }

    private fun clearView()
    {
        setSpinnerAdapter(binding.acceptUserSpinner, this@CreateNewWorkActivity, spinnerArrayLists.workerList)//작업자
        setSpinnerAdapter(binding.equipmentSpinner, this@CreateNewWorkActivity, spinnerArrayLists.equipmentList)//장비
        setSpinnerAdapter(binding.productSpinner, this@CreateNewWorkActivity, spinnerArrayLists.productList)//제품
        setSpinnerAdapter(binding.processSpinner, this@CreateNewWorkActivity, spinnerArrayLists.processList)//공정
        binding.requestAmountEditText.setText("")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }
    private fun errorDialog(message:String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error Message").setMessage(message)
        builder.setPositiveButton("확인") { dialogInterface, i ->
        }
        val dialog = builder.create()
        dialog.show()
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