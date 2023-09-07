package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.Equipment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.bst.rawdatacollector.Utils.SpinnerInterface.CustomSpinnerAdapter
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


class EquipmentInfoFragment : Fragment()
{
    private lateinit var binding: FragmentMachineInfoBinding
    private lateinit var errorType: ArrayList<String>

    private var equipmentErrorChangedListener: EquipmentErrorChangedListener? = null
    private var equipmentStoppedTimeChangedListener: EquipmentStoppedTimeChangedListener? = null
    private var equipmentRestartTimeChangedListener: EquipmentRestartTimeChangedListener? = null
    private var equipmentTimeAmountChangedListener: EquipmentStoppedTimeAmountChangedListener? = null


    private var timeAmount: String = ""
    private var stoppedHour: Int = 0
    private var stoppedMinute: Int = 0
    private var restartHour: Int = 0
    private var restartMinute: Int = 0


    companion object
    {
        private const val SELECT_ERROR_TYPE_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_ErrorType.php/"
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        equipmentErrorChangedListener = context as EquipmentErrorChangedListener
        equipmentStoppedTimeChangedListener = context as EquipmentStoppedTimeChangedListener
        equipmentRestartTimeChangedListener = context as EquipmentRestartTimeChangedListener
        equipmentTimeAmountChangedListener = context as EquipmentStoppedTimeAmountChangedListener
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
            setEquipmentStoppedTimeDialog(binding.startTimeBtn)
        }
        binding.endTimeBtn.setOnClickListener {
            setMachineRestartTimeDialog(binding.endTimeBtn)
            //에러 시작시간보다 작으면 return 시켜야함
        }

    }

    private fun setEquipmentStoppedTimeDialog(btn: Button)
    {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        var h: String = ""//Listener 로 값을 넘겨주기 위한 변수

        //dialogView Init
        val dialogView = inflater.inflate(R.layout.dialog_machine_error_time, null)
        val timePicker: TimePicker = dialogView.findViewById(R.id.timePicker)

        dialogBuilder.setView(dialogView)

        timePicker.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener
        {
            @SuppressLint("SetTextI18n")
            override fun onTimeChanged(_timePicker: TimePicker?, _hour: Int, _minute: Int)
            {
                if (_hour > 10)//오후일때
                {
                    if (_minute > 10)
                    {
                        btn.text = "$_hour : $_minute"
                        h = "$_hour:$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                    else
                    {
                        btn.text = "$_hour : 0$_minute"
                        h = "$_hour:0$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                }
                else//오전일때
                {
                    if (_minute > 10)
                    {
                        btn.text = "0$_hour : $_minute"
                        h = "$_hour:$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                    else
                    {
                        btn.text = "0$_hour : $_minute"
                        h = "$_hour:$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                }
            }
        })

        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            equipmentStoppedTimeChangedListener?.onMachineStoppedTimeChanged(h)
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun setMachineRestartTimeDialog(btn: Button)
    {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        var h: String = ""//Listener 로 값을 넘겨주기 위한 변수

        //dialogView Init
        val dialogView = inflater.inflate(R.layout.dialog_machine_error_time, null)
        val timePicker: TimePicker = dialogView.findViewById(R.id.timePicker)

        dialogBuilder.setView(dialogView)

        timePicker.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener
        {
            @SuppressLint("SetTextI18n")
            override fun onTimeChanged(_timePicker: TimePicker?, _hour: Int, _minute: Int)
            {

                if (_hour > 10)//오후일때
                {
                    if (_minute > 10)
                    {
                        btn.text = "$_hour : $_minute"
                        h = "$_hour:$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                    else
                    {
                        btn.text = "$_hour : 0$_minute"
                        h = "$_hour:0$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                }
                else//오전일때
                {
                    if (_minute > 10)
                    {
                        btn.text = "0$_hour : $_minute"
                        h = "$_hour:$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                    else
                    {
                        btn.text = "0$_hour : $_minute"
                        h = "$_hour:$_minute:00"
                        restartHour = _hour
                        restartMinute = _minute
                    }
                }
            }
        })

        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            equipmentRestartTimeChangedListener?.onMachineRestartTimeChanged(h)
            if (getTimeAmount(stoppedHour, stoppedMinute, restartHour, restartMinute) < 0)
            {
                Toast.makeText(requireContext(), "시간을 다시 확인해 주세요", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            else
            {
                timeAmount = getTimeAmount_12H_Foramt(stoppedHour, stoppedMinute, restartHour, restartMinute)
                equipmentTimeAmountChangedListener?.onMachineStoppedTimeAmountChanged(timeAmount)
            }

            //통신 해야 함
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun getTimeAmount(stoppedHour: Int, startMinute: Int, restartHour: Int, restartMinute: Int): Int
    {
        val firstTimeAmount = stoppedHour * 60 + startMinute
        val secondTimeAmount = restartHour * 60 + restartMinute

        return secondTimeAmount - firstTimeAmount
    }

    private fun getTimeAmount_12H_Foramt(stoppedHour: Int, startMinute: Int, restartHour: Int, restartMinute: Int): String
    {
        val timeAmount = getTimeAmount(stoppedHour, startMinute, restartHour, restartMinute)
        val hour = timeAmount / 60
        val minute = timeAmount % 60
        return "$hour 시간 $minute 분"
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
                equipmentErrorChangedListener?.onMachineErrorTypeChanged(selectedItem)
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }
    }

    interface EquipmentErrorChangedListener
    {
        fun onMachineErrorTypeChanged(errorType: String)
    }

    interface EquipmentStoppedTimeChangedListener
    {
        fun onMachineStoppedTimeChanged(machineStoppedTime: String)
    }

    interface EquipmentRestartTimeChangedListener
    {
        fun onMachineRestartTimeChanged(machineRestartTime: String)
    }
    interface EquipmentStoppedTimeAmountChangedListener
    {
        fun onMachineStoppedTimeAmountChanged(machineTimeAmount:String)
    }

}