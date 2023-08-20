package com.bst.rawdatacollector.MainActivity.ProductListFragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.Delegate.VoidVoidDelegate
import com.bst.rawdatacollector.DataClass.Product
import com.bst.rawdatacollector.MainActivity.MainActivity
import com.bst.rawdatacollector.ProductInfo.ProductAdapter
import com.bst.rawdatacollector.ProductInfo.ProductInfoActivity
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
    private lateinit var productList: ArrayList<Product>

    companion object{
        private const val PRODUCT_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_Today_Products.php"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(_inflater: LayoutInflater, _container: ViewGroup?, _savedInstanceState: Bundle?): View
    {
        binding = FragmentProductListBinding.inflate(_inflater, _container, false)
        productList = ArrayList()


        //setTestList()

        selectTodayProductLists(UserData.getInstance(requireContext()).getUserCode(),LocalDate.now().toString())

        productAdapter = ProductAdapter(requireContext(), productList)//kotlin 은 requireContext()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            binding.dayText.text = LocalDate.now().toString()//오늘 날짜를 나타냄
        }

        binding.recyclerView.adapter = productAdapter//set adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager//setLayoutManger

        productAdapter.setProductItemTouchCallback(object : VoidVoidDelegate
        {
            override fun voidVoidDelegate()
            {
                val intent: Intent = Intent(requireContext(), ProductInfoActivity::class.java)
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

                            for (i in 0 until jsonArray.length())
                            {
                                val json: JSONObject = jsonArray.getJSONObject(i)
                                val product = Product()
                                product.productName = json.getString("product_name")
                                product.productCode = json.getString("product_code")
                                product.requestName = json.getString("request_user")
                                product.acceptName = json.getString("accept_user")
                                product.productImg = json.getString("product_image")
                                productList.add(product)
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