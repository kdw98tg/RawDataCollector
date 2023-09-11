package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolsInfoFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Tool
import com.bst.rawdatacollector.Delegate.VoidVoidDelegate
import com.bst.rawdatacollector.MainActivity.Main_Manager.MainActivityManager
import com.bst.rawdatacollector.MainActivity.Main_Manager.ToolInfoActivity.ToolInfoActivity
import com.bst.rawdatacollector.MainActivity.Main_Worker.MainActivityWorker
import com.bst.rawdatacollector.databinding.FragmentToolsInfoBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.notify
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ToolsInfoFragment : Fragment()
{

    private lateinit var binding: FragmentToolsInfoBinding
    private lateinit var toolAdapter: ToolInfoAdapter
    private lateinit var toolList: ArrayList<Tool>

    companion object
    {
        private const val SELECT_TOOLS_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_ToolList.php/"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentToolsInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //init
        toolList = ArrayList()
        toolAdapter = ToolInfoAdapter(requireContext(), toolList)

        binding.toolRecyclerView.adapter = toolAdapter
        binding.toolRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        selectToolList()

        //item 클릭 시 ToolInfoActivity 로 넘어 가기 위한 콜백
        toolAdapter.setToolInfoClickedListener(object : ToolInfoAdapter.ToolInfoClickedListener
        {
            override fun onClicked()
            {
                val intent = Intent(requireContext(), ToolInfoActivity::class.java)
                startActivity(intent)
            }

        })

    }

    private fun selectToolList()
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
                    try
                    {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        //JsonObject 해체 작업
                        val json = jsonArray.getJSONObject(0)
                        val toolCode = json.getString("tool_code").toString()
                        val toolName = json.getString("tool_name").toString()
                        val toolImg = json.getString("tool_image").toString()
                        val toolUsesAmount = json.getString("uses_amount").toString()

                        val tool: Tool = Tool(toolCode, toolName, toolImg, toolUsesAmount.toInt())
                        toolList.add(tool)

                        activity?.runOnUiThread {
                            toolAdapter.notifyDataSetChanged()
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