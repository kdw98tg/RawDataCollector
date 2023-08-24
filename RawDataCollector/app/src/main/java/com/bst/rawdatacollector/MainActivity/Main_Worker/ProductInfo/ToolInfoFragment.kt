package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.FragmentToolInfoBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class ToolInfoFragment : Fragment()
{
    private lateinit var binding:FragmentToolInfoBinding
    private lateinit var errorType:ArrayList<String>

    companion object{
        private const val SELECT_ERROR_TYPE_URL ="http://kdw98tg.dothome.co.kr/RDC/Select_ErrorType.php/"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentToolInfoBinding.inflate(layoutInflater)

        //init
        errorType=ArrayList()

        selectErrorType("도구")

        return binding.root
    }

    private fun selectErrorType(_type:String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("type",_type).build()
        val request = Request.Builder().url(SELECT_ERROR_TYPE_URL).post(body).build()

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
                    activity?.runOnUiThread {
                        try
                        {
                            val jsonObject = JSONObject(response.body!!.string())
                            val jsonArray = JSONArray(jsonObject.getString("results"))

                            for (i in 0 until jsonArray.length())
                            {
                                val json: JSONObject = jsonArray.getJSONObject(i)
                                val errorName = json.getString("error_name")
                                errorType.add(errorName)

                                //setSpinnerAdapter(binding.workSettingSpinner,requireContext(), errorType)
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

    private fun setSpinnerAdapter(spinner: Spinner, context: Context, stringLists: ArrayList<String>)
    {
        val spinnerAdapter: CustomSpinnerAdapter = CustomSpinnerAdapter(context, stringLists)
        spinner.adapter = spinnerAdapter//어뎁터 생성
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                //고른 아이템을 반환
                val selectedItem = spinnerAdapter.getItem()
                Toast.makeText(context, "선택된 아이템:$selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }

    }

}