package com.bst.rawdatacollector.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bst.rawdatacollector.MainActivity.Main_Manager.MainActivityManager
import com.bst.rawdatacollector.MainActivity.Main_Worker.MainActivityWorker
import com.bst.rawdatacollector.Register.RegisterActivity
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.Utils.Utils.SharedPreferences.MySharedPreferences
import com.bst.rawdatacollector.Utils.Utils.SharedPreferences.SharedPreferencesProperties
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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    companion object {
        private const val LOGIN_URL = "http://kdw98tg.dothome.co.kr/RDC/Select_User_Login.php"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init


        binding.loginBtn.setOnClickListener {
            binding.loginBtn.isEnabled = false
            val userId = binding.idText.text.toString().trim() { it <= ' ' }//id 추출
            val userPw = binding.pwText.text.toString().trim() { it <= ' ' }//pw 추출
            if (MySharedPreferences.getBoolean(this@LoginActivity, SharedPreferencesProperties.autoLogin, false)) {
                //통신해서 SharedPref에 값을 넣어야 함
            } else {
                loginRequest(userId, userPw)//로그인 요청
            }
        }
        if (binding.autoLoginCheckBox.isChecked) {
            MySharedPreferences.putBoolean(this@LoginActivity, SharedPreferencesProperties.autoLogin, true)
        }
        else{
            MySharedPreferences.putBoolean(this@LoginActivity,SharedPreferencesProperties.autoLogin,false);
        }

        binding.registerBtn.setOnClickListener {
            moveActivity(RegisterActivity::class.java)
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.loginBtn.isEnabled = true
    }

    private fun loginRequest(_userId: String, _userPw: String) {
        val client = OkHttpClient()

        val body = FormBody.Builder().add("userCode", _userId).add("userPw", _userPw).build()

        val request = Request.Builder().url(LOGIN_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "인터넷 연결을 확인해 주세요", Toast.LENGTH_SHORT).show()
                    binding.loginBtn.isEnabled = true
                    e.printStackTrace()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {

                    val result = response.body!!.string()
                    try {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        Log.d("로그인", "onResponse: ${jsonObject}")

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
                        val userWage = json.getString("wage").toInt()
                        //userData에 집어넣음
                        setUserData(
                            userCode,
                            userPw,
                            userName,
                            userEmail,
                            userCompany,
                            userPhoneNumber,
                            userProfileImg,
                            userPosition,
                            userWage
                        )

                        //다 됐으면 권한에 따라 MainActivity로 옮김

                        if (userPosition == "사원")//사원이면 작업자 메인으로
                        {
                            moveActivity(MainActivityWorker::class.java)
                        } else if (userPosition == "관리자")//관리자면 관리자 화면으로
                        {
                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    "관리자 모드로 로그인 하셨습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            moveActivity(MainActivityManager::class.java)
                        }
                    } catch (e: Exception)//로그인 실패시
                    {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "사번 또는 비밀번호를 확인하세요",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.loginBtn.isEnabled = true
                        }
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
        userPosition: String,
        userWage: Int
    ) {
        val userData = UserData.getInstance(this@LoginActivity)
        userData.userCode = userCode
        userData.userPw = userPw
        userData.userName = userName
        userData.userEmail = userEmail
        userData.userCompany = userCompany
        userData.userPhoneNumber = userPhoneNumber
        userData.userProfileImg = userProfileImg
        userData.userPosition = userPosition
        userData.userWage = userWage
    }

    private fun moveActivity(_activityClass: Class<*>)//액티비티를 받아서 intent 수행
    {
        val intent = Intent(this@LoginActivity, _activityClass)
        startActivity(intent)
    }
}