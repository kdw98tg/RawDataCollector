package com.bst.rawdatacollector.MainActivity.Main_Worker.AttendanceFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.MainActivityWorker
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.UserInfo.UserInfoActivity
import com.bst.rawdatacollector.Utils.Utils.SharedPreferences.MySharedPreferences
import com.bst.rawdatacollector.Utils.Utils.SharedPreferences.SharedPreferencesProperties
import com.bst.rawdatacollector.Utils.Utils.URL.URLManager
import com.bst.rawdatacollector.databinding.FragmentAttendanceBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


class AttendanceFragment : Fragment()
{
    private lateinit var binding: FragmentAttendanceBinding


    companion object
    {
        private const val INSERT_START_TIME_URL = "${URLManager.PHP_URL}Insert_StartTime.php/"
        private const val INSERT_END_TIME_URL = "${URLManager.PHP_URL}Insert_EndTime.php/"
        private const val SELECT_TOTAL_WORK_TIME_URL = "${URLManager.PHP_URL}Select_TotalWorkTime.php/"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentAttendanceBinding.inflate(layoutInflater)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        selectTotalWorkTime(UserData.getInstance(requireContext()).userCode)//총 근무시간 Select 후 텍스트 지정

        //viewInit
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            binding.dayText.text = LocalDate.now().toString()
        }
        //시작하자마자 퇴근 못하기 때문에 false
        if (!MySharedPreferences.getBoolean(requireContext(), SharedPreferencesProperties.isAttendance, false))//출근한 상태가 아니면
        {
            binding.attendanceBtn.isEnabled = true
            binding.endBtn.isEnabled = false
        }
        else if(MySharedPreferences.getBoolean(requireContext(),SharedPreferencesProperties.attendanceComplete,false))//출근을 완료한 상태라면
        {
            //둘다 끌거임
            binding.attendanceBtn.isEnabled = false
            binding.endBtn.isEnabled = false
        }


        //출근
        binding.attendanceBtn.setOnClickListener {
            if (!MySharedPreferences.getBoolean(requireContext(), SharedPreferencesProperties.isAttendance, false))//출근한 상태가 아니라면
            {
                getAttendanceDialog()//출근Dialog 띄움
            }
            else
            {
                attendanceErrorDialog("이미 출근을 완료 하셨습니다. 관리자에게 문의하세요.")//버그가 생긴거니까 관리자한테 문의 하라고 함
            }
        }
        //퇴근
        binding.endBtn.setOnClickListener {

            if (MySharedPreferences.getBoolean(requireContext(), "isAttendance", false))//출근한 상태라면
            {
                workEndDialog()//퇴근 Dialog띄움
            }
            else
            {
                attendanceErrorDialog("이미 퇴근을 완료 하셨습니다.")//이미 퇴근한거임
            }
        }
        //회원정보 조회
        binding.userInfoImg.setOnClickListener {
            val intent = Intent(requireContext(), UserInfoActivity::class.java)
            startActivity(intent)
        }

        //조퇴신청버튼
        binding.leaveEarlyBtn.setOnClickListener {
            applyLeaveEarlyDialog()
        }
    }

    private fun selectTotalWorkTime(userCode: String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode", userCode).build()
        val request = Request.Builder().url(SELECT_TOTAL_WORK_TIME_URL).post(body).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
            }

            @SuppressLint("SetTextI18n")
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
                        val totalWorkTime = json.getString("total_work_time").toString()

                        UserData.getInstance(requireContext()).userTotalWorkTime = totalWorkTime.toInt()

                        activity?.runOnUiThread {
                            binding.totalWorkTime.text = totalWorkTime + "시간"
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertStartTime(userCode: String, curDateTime: String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode", userCode).add("curDateTime", curDateTime.toString()).build()
        val request = Request.Builder().url(INSERT_START_TIME_URL).post(body).build()

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
                        Toast.makeText(requireContext(), "출근 완료 하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun insertEndTime(userCode: String, curDateTime: String)
    {
        val client = OkHttpClient()
        val body = FormBody.Builder().add("userCode", userCode).add("curDateTime", curDateTime).build()
        val request = Request.Builder().url(INSERT_END_TIME_URL).post(body).build()

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
                        Toast.makeText(requireContext(), "퇴근 완료 하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun applyLeaveEarlyDialog()
    {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())

        //dialogView Init
        val dialogView = inflater.inflate(R.layout.dialog_apply_leave_early, null)
        val leaveEarlyEditText = dialogView.findViewById<EditText>(R.id.leaveEarlyEditText)

        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("조퇴 신청").setMessage("조퇴 사유를 작성해 주세요.")
        dialogBuilder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
            dialog.dismiss()
        }.setPositiveButton("확인") { dialog, which ->
            //조퇴사유 데이터베이스에 백업
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAttendanceDialog()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("출석").setMessage("출석 하시겠습니까?")
        builder.setPositiveButton("확인") { dialogInterface, i ->
            insertStartTime(UserData.getInstance(requireContext()).userCode, getCurTime())
            Log.d("데이트타임", "attendanceErrorDialog:  ${getCurTime()}")
            //isAttendance = true
            //sharedPreferences 에 저장
            MySharedPreferences.putBoolean(requireContext(), SharedPreferencesProperties.isAttendance, true)
            binding.attendanceBtn.isEnabled = false
            binding.endBtn.isEnabled = true
            binding.chronometer.start()//크로노미터
        }
        builder.setNegativeButton("취소") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun workEndDialog()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("퇴근").setMessage("퇴근 하시겠습니까?")
        builder.setPositiveButton("확인") { dialogInterface, i ->
            insertEndTime(UserData.getInstance(requireContext()).userCode, getCurTime())
            binding.endBtn.isEnabled = false
            binding.chronometer.stop()//크로노미터
            MySharedPreferences.putBoolean(requireContext(),SharedPreferencesProperties.attendanceComplete,true)
            //MySharedPreferences.putBoolean(requireContext(),"isAttendance",false)
        }
        builder.setNegativeButton("취소") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun attendanceErrorDialog(message: String)
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error Message").setMessage(message)
        builder.setPositiveButton("확인") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurTime(): String
    {
        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(now)
        return getCurDate().toString() + ' ' + simpleDateFormat
    }


}


