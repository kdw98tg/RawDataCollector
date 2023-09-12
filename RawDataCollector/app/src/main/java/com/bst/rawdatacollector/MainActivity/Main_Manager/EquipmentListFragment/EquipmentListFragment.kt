package com.bst.rawdatacollector.MainActivity.Main_Manager.EquipmentListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Equipment
import com.bst.rawdatacollector.databinding.FragmentEquipmentInfoBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class EquipmentListFragment : Fragment()
{

    private lateinit var binding: FragmentEquipmentInfoBinding
    private lateinit var equipmentAdapter: EquipmentListAdapter
    private lateinit var equipmentList: ArrayList<Equipment>

    companion object
    {
        private const val SELECT_EQUIPMENT_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_EquipmentList.php/"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentEquipmentInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //init
        equipmentList = ArrayList()
        equipmentAdapter = EquipmentListAdapter(requireContext(), equipmentList)

        selectEquipmentList()//쿼리 손보기

        binding.equipmentRecyclerView.adapter = equipmentAdapter
        binding.equipmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun selectEquipmentList()
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_EQUIPMENT_URL).build()
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
                        val equipmentCode = json.getString("equipment_code").toString()
                        val equipmentImg = json.getString("equipment_name").toString()
                        val equipmentName = json.getString("equipment_image").toString()

                        val equipment = Equipment(equipmentCode, equipmentImg,equipmentName)
                        equipmentList.add(equipment)

                        activity?.runOnUiThread {
                            equipmentAdapter.notifyDataSetChanged()
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