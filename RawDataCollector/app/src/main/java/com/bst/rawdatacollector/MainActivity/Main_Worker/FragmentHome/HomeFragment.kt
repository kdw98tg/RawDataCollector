package com.bst.rawdatacollector.MainActivity.Main_Worker.FragmentHome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.UserInfo.UserInfoActivity
import com.bst.rawdatacollector.databinding.FragmentHomeBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date


class HomeFragment : Fragment()
{
    private lateinit var binding: FragmentHomeBinding

    companion object
    {
        private const val INSERT_START_TIME_URL = "http://kdw98tg.dothome.co.kr/RDC/Insert_StartTime.php/"
        private const val INSERT_END_TIME_URL = "http://kdw98tg.dothome.co.kr/RDC/Insert_EndTime.php/"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //viewInit
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            binding.dayText.text = LocalDate.now().toString()
        }
        //출근
        binding.attendanceBtn.setOnClickListener {
            Log.d("시간", "onCreateView: ${getCurDate()}, ${getCurTime()}")
            insertStartTime(UserData.getInstance(requireContext()).userCode,getCurDate(),getCurTime())
        }
        //퇴근
        binding.endBtn.setOnClickListener {
            insertEndTime(UserData.getInstance(requireContext()).userCode,getCurDate(),getCurTime())
        }
        //회원정보 조회
        binding.userInfoImg.setOnClickListener {
            val intent = Intent(requireContext(), UserInfoActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertStartTime(userCode: String, curDate: LocalDate, now: String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode", userCode).add("curDate", curDate.toString()).add("curTime", now.toString()).build()
        val request = Request.Builder().url(INSERT_START_TIME_URL).post(body).build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if(response.isSuccessful)
                {
                    activity?.runOnUiThread {
                    Toast.makeText(requireContext(),"출근 완료 하였습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun insertEndTime(userCode:String, curDate:LocalDate, now:String){
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode", userCode).add("curDate", curDate.toString()).add("curTime", now.toString()).build()
        val request = Request.Builder().url(INSERT_END_TIME_URL).post(body).build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response)
            {
                if(response.isSuccessful)
                {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(),"퇴근 완료 하였습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }

    private fun getCurTime(): String
    {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return dateFormat.format(date)
    }


}


