package com.bst.rawdatacollector.MainActivity.Main_Manager.ProductInfoFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Product
import com.bst.rawdatacollector.MainActivity.Main_Manager.ProductInfoActivity.ProductInfoActivity
import com.bst.rawdatacollector.databinding.FragmentProductInfoBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class ProductListFragment : Fragment()
{

    private lateinit var binding: FragmentProductInfoBinding
    private lateinit var productAdapter:ProductListAdapter
    private lateinit var productList:ArrayList<Product>

    companion object{
        private const val SELECT_PRODUCT_LIST = "http://kdw98tg.dothome.co.kr/RDC/Select_ProductList.php/"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProductInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //init
        productList = ArrayList()
        productAdapter = ProductListAdapter(requireContext(),productList)

        selectProductList()

        binding.productRecyclerView.adapter = productAdapter
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        productAdapter.setProductClickedListener(object: ProductListAdapter.ProductClickedListener{

            override fun onClicked(product: Product)
            {
                val intent:Intent = Intent(requireContext(),ProductInfoActivity::class.java)
                intent.putExtra("productCode",product.productCode)
                intent.putExtra("productName",product.productName)
                intent.putExtra("productImg",product.productImg)
                intent.putExtra("customer",product.customer)
                startActivity(intent)
            }
        })

    }
    private fun selectProductList()
    {
        val client = OkHttpClient()
        val request = Request.Builder().url(SELECT_PRODUCT_LIST).build()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if(response.isSuccessful)
                {
                    val result = response.body!!.string()
                    try
                    {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        //JsonObject 해체 작업
                        val json = jsonArray.getJSONObject(0)
                        val productCode = json.getString("product_code").toString()
                        val productName = json.getString("product_name").toString()
                        val productImage = json.getString("product_image").toString()
                        val customer = json.getString("customer").toString()

                        val product: Product = Product(productCode, productName, productImage, customer)
                        productList.add(product)

                        activity?.runOnUiThread {
                            productAdapter.notifyDataSetChanged()
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