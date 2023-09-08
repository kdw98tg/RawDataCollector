package com.bst.rawdatacollector.MainActivity.Main_Manager.AllProjectListsFragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Producing
import com.bst.rawdatacollector.MainActivity.Main_Manager.CreateNewWork.CreateNewWorkActivity
import com.bst.rawdatacollector.databinding.FragmentProductListManagerBinding
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


class AllProductListFragment : Fragment()
{

    private lateinit var binding: FragmentProductListManagerBinding
    private lateinit var allProductAdapter: AllProducingAdapter
    private lateinit var allProducingList: ArrayList<Producing>


    companion object
    {
        private const val SELECT_PRODUCING_LIST_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_AllProducingLists.php"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProductListManagerBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //init
        allProducingList = ArrayList()
        allProductAdapter = AllProducingAdapter(requireContext(), allProducingList)
        binding.dayText.text = getCurDate().toString()

        binding.recyclerView.adapter = allProductAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        selectAllProducingLists(getCurDate().toString())//모든 작업을 가져 오는 함수

        //작업 추가로 넘어감
        binding.workDistributeFab.setOnClickListener {
            val intent = Intent(requireContext(), CreateNewWorkActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }

    private fun selectAllProducingLists(curDate: String)
    {
        val client: OkHttpClient = OkHttpClient()
        val body = FormBody.Builder().add("curDate", curDate).build()
        val request = Request.Builder().url(SELECT_PRODUCING_LIST_URL).post(body).build()
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

                    try
                    {
                        val jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        for (i in 0 until jsonArray.length())
                        {
                            val json: JSONObject = jsonArray.getJSONObject(i)
                            val producing = Producing()
                            producing.productName = json.getString("product_name")
                            producing.productCode = json.getString("product_code")
                            producing.requestName = json.getString("request_user")
                            producing.acceptName = json.getString("accept_user")
                            producing.productImg = json.getString("product_image")
                            producing.equipmentCode = json.getString("equipment_code")
                            producing.process = json.getString("process")

                            allProducingList.add(producing)

                            activity?.runOnUiThread {
                                allProductAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    catch (_e: Exception)
                    {
                        _e.printStackTrace()
                    }

                }
            }

        })
    }
}