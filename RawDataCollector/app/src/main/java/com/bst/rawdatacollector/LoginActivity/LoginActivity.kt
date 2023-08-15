package com.bst.rawdatacollector.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bst.rawdatacollector.MainActivity.MainActivity
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.ActivityLoginBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityLoginBinding


    companion object
    {
        private const val LOGIN_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_User_Login.php"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init

        binding.loginBtn.setOnClickListener {
            val userId = binding.idText.text.toString()//id 추출
            val userPw = binding.pwText.text.toString()//pw 추출
            loginRequest(userId, userPw)//로그인 요청
        }

    }

    private fun loginRequest(_userId: String, _userPw: String)
    {
        val client = OkHttpClient()

        val body = FormBody.Builder().add("userCode", _userId).build()

        val request = Request.Builder().url(LOGIN_URL).post(body).build()

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
                    val result = response.body!!.string()
                    Log.d("로그인", "onResponse: $result")
                    try
                    {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        //JsonObject 해체 작업
                        val json = jsonArray.getJSONObject(0)
                        val userCode = json.getString("user_code").toString()
                        val userPw = json.getString("pw").toString()
                        val userName = json.getString("name").toString()
                        val userEmail = json.getString("email").toString()
                        val userCompany = json.getString("company").toString()
                        val userPhoneNumber = json.getString("phone_number").toString()
                        val userProfileImg = json.getString("profile_img").toString()
                        val userPosition = json.getString("position").toString()

                        //userData에 집어넣음
                        setUserData(userCode, userPw, userName, userEmail, userCompany, userPhoneNumber, userProfileImg, userPosition)

                        //다 됐으면 권한에 따라 MainActivity로 옮김
                        if(userPosition == "사원")//사원이면 메인으로
                        {
                            moveActivity(MainActivity::class.java)
                        }
                        else//관리자면 관리자 화면으로
                        {
                            runOnUiThread{

                            Toast.makeText(applicationContext,"관리자 모드로 로그인 하셨습니다",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    catch (e: Exception)
                    {
                        e.printStackTrace()
                    }

                }
            }

        })
    }

    private fun setUserData(
        userCode: String,
        userPw: String,
        userName: String,
        userEmail: String,
        userCompany: String,
        userPhoneNumber: String,
        userProfileImg: String,
        userPosition: String)
    {
        val userData = UserData.getInstance(this@LoginActivity)
        userData.setUserCode(userCode)
        userData.setUserPw(userPw)
        userData.setUserName(userName)
        userData.setUserEmail(userEmail)
        userData.setUserCompany(userCompany)
        userData.setUserPhoneNumber(userPhoneNumber)
        userData.setUserProfileImg(userProfileImg)
        userData.setUserPosition(userPosition)
    }

    private fun moveActivity(_activityClass: Class<*>)//액티비티를 받아서 intent 수행
    {
        val intent = Intent(this@LoginActivity, _activityClass)
        startActivity(intent)
    }
}