package com.bst.rawdatacollector.MainActivity.Main_Worker.WorkHistory

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.WorkList
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.Utils.Utils.URL.URLManager
import com.bst.rawdatacollector.databinding.ActivityWorkHistoryBinding
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

class WorkHistoryActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityWorkHistoryBinding
    private lateinit var workHistoryAdapter:WorkHistoryAdapter
    private lateinit var workHistoryList:ArrayList<WorkList>

    private var selectedDate:String? = null

    companion object{
        private const val SELECT_WORK_HISTORY_URL = "${URLManager.PHP_URL}Select_WorkHistory.php"
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        workHistoryList = ArrayList()
        workHistoryAdapter = WorkHistoryAdapter(this@WorkHistoryActivity, workHistoryList)

        //viewInit
        binding.curDateText.text = getCurDate().toString()
        binding.workWage.text = UserData.getInstance(this@WorkHistoryActivity).userWage.toString() + "원"


        //setRecyclerView
        binding.workHistoryRecyclerView.adapter = workHistoryAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        binding.workHistoryRecyclerView.layoutManager = linearLayoutManager

        selectedDate = intent.getStringExtra("selectedDate")
        Log.d("지정된 날짜", "onCreate: $selectedDate , ${UserData.getInstance(this@WorkHistoryActivity).userCode}")
        selectCurWorkHistory(UserData.getInstance(this@WorkHistoryActivity).userCode, selectedDate!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }


    private fun selectCurWorkHistory(_userCode:String, _selectedDate:String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode",_userCode).add("selectedDate",_selectedDate).build()
        val request = Request.Builder().url(SELECT_WORK_HISTORY_URL).post(body).build()

        client.newCall(request).enqueue(object: Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if(response.isSuccessful)
                {
                    runOnUiThread {
                        try
                        {
                            val jsonObject = JSONObject(response.body!!.string())
                            val jsonArray = JSONArray(jsonObject.getString("results"))

                            for (i in 0 until jsonArray.length())
                            {
                                val json: JSONObject = jsonArray.getJSONObject(i)
                                val workList = WorkList()
                                workList.workProcess = json.getString("product_code")
                                workList.workAmount = json.getString("done_amount")

                                workHistoryList.add(workList)
                                workHistoryAdapter.notifyDataSetChanged()
                            }
                        }
                        catch (e: Exception)
                        {
                            e.printStackTrace()
                        }
                    }
                }
            }
        })
    }
}