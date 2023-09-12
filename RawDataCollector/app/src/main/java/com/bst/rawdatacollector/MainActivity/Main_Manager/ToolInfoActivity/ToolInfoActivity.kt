package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolInfoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Tool
import com.bst.rawdatacollector.DataClass.ToolUses
import com.bst.rawdatacollector.MainActivity.Main_Manager.ToolsInfoFragment.ToolsListFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityToolInfoBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ToolInfoActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityToolInfoBinding
    private lateinit var toolInfoList:ArrayList<ToolUses>
    private lateinit var toolInfoAdapter:ToolInfoAdapter


    private var toolCode: String? = null
    private var toolName: String? = null
    private var toolSerialNumber: String? = null
    private var toolImage: String? = null
    private var usesAmount: String? = null

    companion object{
        private const val SELECT_TOOL_USES_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_ToolUses.php/"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityToolInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        toolInfoList = ArrayList()
        toolInfoAdapter = ToolInfoAdapter(this@ToolInfoActivity,toolInfoList)

        toolCode = intent.getStringExtra("toolCode")
        toolName = intent.getStringExtra("toolName")
        toolSerialNumber = intent.getStringExtra("toolSerialNumber")
        toolImage = intent.getStringExtra("toolImage")
        usesAmount = intent.getStringExtra("usesAmount").toString()

        binding.toolCodeText.text = toolCode
        binding.toolNameText.text = toolName
        binding.toolSerialNumberText.text = toolSerialNumber
        binding.toolUsesAmountText.text = usesAmount

        selectToolUses(toolCode!!)

        binding.usesRecyclerView.adapter = toolInfoAdapter
        binding.usesRecyclerView.layoutManager = LinearLayoutManager(this@ToolInfoActivity)
    }

    private fun selectToolUses(toolCode:String)
    {
        val client:OkHttpClient = OkHttpClient()
        val body = FormBody.Builder().add("toolCode",toolCode).build()
        val request = Request.Builder().url(SELECT_TOOL_USES_URL).post(body).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful)
                {
                    val result = response.body!!.string()
                    try
                    {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        Log.d("툴 사용처", "onResponse: $jsonObject")

                        for(i in 0 until jsonArray.length()) {
                            //JsonObject 해체 작업
                            val json = jsonArray.getJSONObject(i)
                            val date = json.getString("date").toString()
                            val serialNumber = json.getString("serial_number").toString()
                            val equipmentCode = json.getString("equipment_code").toString()
                            val doneAmount = json.getString("done_amount").toString()

                            val toolUses = ToolUses(date, serialNumber, equipmentCode, doneAmount)
                            toolInfoList.add(toolUses)

                            runOnUiThread {
                                toolInfoAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    catch (e: Exception)//로그인 실패시
                    {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
}