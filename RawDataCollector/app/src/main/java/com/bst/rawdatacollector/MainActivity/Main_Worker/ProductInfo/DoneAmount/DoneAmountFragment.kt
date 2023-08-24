package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.DoneAmount

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.ProductError
import com.bst.rawdatacollector.DataClass.WorkList
import com.bst.rawdatacollector.MainActivity.Main_Worker.WorkHistory.WorkHistoryActivity
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.FragmentDoneAmountBinding
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


class DoneAmountFragment : Fragment()
{

    private lateinit var binding:FragmentDoneAmountBinding
    private lateinit var errorListAdapter:ErrorListAdapter
    private lateinit var errorList:ArrayList<ProductError>
    private lateinit var errorType:ArrayList<String>

    companion object{
        private const val SELECT_ERROR_TYPE_URL ="http://kdw98tg.dothome.co.kr/RDC/Select_ErrorType.php/"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentDoneAmountBinding.inflate(layoutInflater)

        //init
        errorList = ArrayList()
        errorType=ArrayList()
        errorListAdapter = ErrorListAdapter(requireContext(), errorList, errorType)

        selectErrorType("제품")

        //setRecyclerView
        binding.errorListRecyclerView.adapter = errorListAdapter
        binding.errorListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addErrorListBtn.setOnClickListener{
            val error:ProductError = ProductError()
            errorList.add(error)
            errorListAdapter.notifyDataSetChanged()
        }

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
                                errorListAdapter.notifyDataSetChanged()
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