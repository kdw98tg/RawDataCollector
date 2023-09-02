package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductListFragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Producing
import com.bst.rawdatacollector.Delegate.ProductClickListener
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.ProductInfoActivity
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.FragmentProductListBinding
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


class ProductListFragment() : Fragment()
{
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: ArrayList<Producing>

    companion object{
        private const val PRODUCT_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_Today_Products.php"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(_inflater: LayoutInflater, _container: ViewGroup?, _savedInstanceState: Bundle?): View
    {
        binding = FragmentProductListBinding.inflate(_inflater, _container, false)
        productList = ArrayList()


        //setTestList()

        selectTodayProductLists(UserData.getInstance(requireContext()).userCode,LocalDate.now().toString())

        productAdapter = ProductAdapter(requireContext(), productList)//kotlin 은 requireContext()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            binding.dayText.text = LocalDate.now().toString()//오늘 날짜를 나타냄
        }

        binding.recyclerView.adapter = productAdapter//set adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager//setLayoutManger

        productAdapter.setProductItemTouchCallback(object : ProductClickListener
        {

            override fun productSelected(productName: String, productCode: String, requestName: String, acceptName: String,equipmentCode:String)
            {
                val intent: Intent = Intent(requireContext(), ProductInfoActivity::class.java)
                intent.putExtra("productName",productName)
                intent.putExtra("productCode",productCode)
                intent.putExtra("requestName",requestName)
                intent.putExtra("acceptName",acceptName)
                intent.putExtra("equipmentCode",equipmentCode)
                startActivity(intent)
            }

        })

        return binding.root
    }

    private fun selectTodayProductLists(_userCode:String, _curDate : String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode",_userCode).add("curDate",_curDate).build()
        val request = Request.Builder().url(PRODUCT_URL).post(body).build()

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
                    //val mainActivity = requireContext() as MainActivity //runOnUiThread 사용하기 위한 방법
                    activity?.runOnUiThread {
                        try
                        {
                            val jsonObject = JSONObject(response.body!!.string())
                            val jsonArray = JSONArray(jsonObject.getString("results"))
                            Log.d("바디", "onResponse: $jsonObject")

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
                                Log.d("장비번호1", "onResponse: ${producing.equipmentCode}")
                                productList.add(producing)
                                productAdapter.notifyDataSetChanged()
                            }
                        }
                        catch (_e: Exception)
                        {
                            _e.printStackTrace()
                        }
                    }
                }
            }
        })

    }
}