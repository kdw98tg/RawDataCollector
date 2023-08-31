package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.Machine

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.SpinnerInterface.CustomSpinnerAdapter
import com.bst.rawdatacollector.databinding.FragmentMachineInfoBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class MachineInfoFragment : Fragment()
{
    private lateinit var binding: FragmentMachineInfoBinding
    private lateinit var errorType: ArrayList<String>

    companion object
    {
        private const val SELECT_ERROR_TYPE_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_ErrorType.php/"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMachineInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        //init
        errorType = ArrayList()
        selectErrorType("설비")

        binding.startTimeBtn.setOnClickListener {
            showMachineErrorTimeDialog(binding.startTimeBtn)
        }
        binding.endTimeBtn.setOnClickListener {
            showMachineErrorTimeDialog(binding.endTimeBtn)
        }

    }

    private fun showMachineErrorTimeDialog(btn: Button)
    {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())

        //dialogView Init
        val dialogView = inflater.inflate(R.layout.dialog_machine_error_time, null)
        val timePicker: TimePicker = dialogView.findViewById(R.id.timePicker)

        dialogBuilder.setView(dialogView)

        timePicker.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener
        {
            @SuppressLint("SetTextI18n")
            override fun onTimeChanged(_timePicker: TimePicker?, _hour: Int, _minute: Int)
            {
                if (_hour > 12)
                {
                    btn.text = "오후 $_hour : $_minute"
                }
                else
                {
                    btn.text = "오전 $_hour : $_minute"
                }
            }
        })

        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            Toast.makeText(requireContext(), "저장 되었습니다.", Toast.LENGTH_SHORT).show()


            //통신 해야 함
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun selectErrorType(_type: String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("type", _type).build()
        val request = Request.Builder().url(SELECT_ERROR_TYPE_URL).post(body).build()

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

                                setSpinnerAdapter(binding.workSettingSpinner, requireContext(), errorType)
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
        val spinnerAdapter = CustomSpinnerAdapter(context, stringLists)
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