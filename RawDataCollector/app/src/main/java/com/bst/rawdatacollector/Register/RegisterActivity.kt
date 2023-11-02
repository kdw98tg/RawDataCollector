package com.bst.rawdatacollector.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bst.rawdatacollector.LoginActivity.LoginActivity
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.Utils.Utils.URL.URLManager
import com.bst.rawdatacollector.databinding.ActivityRegisterBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RegisterActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityRegisterBinding

    companion object
    {
        private const val REGISTER_URL = "${URLManager.PHP_URL}Insert_UserInfo.php"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {

            val userCode = binding.userCode.text.toString()
            val userPw = binding.userPw.text.toString()
            val userName = binding.userName.text.toString()
            val userCompany = binding.companyName.text.toString()
            val userPhoneNumber = binding.phoneNum.text.toString()
            val userEmail = binding.phoneNum.text.toString()
            //TODO 나중에 페이지 추가해서 자동으로 받아오도록 변경
            val userPosition = "사원"
            val userProfileImg = ""

            insertUserInfo(userCode, userPw, userName, userEmail, userCompany, userPhoneNumber, userProfileImg, userPosition)

            //TODO 액티비티간 전환 말고 프래그먼트로 고민해보기
//            val intent = Intent()
//            intent.putExtra("userCode",userCode)
//            intent.putExtra("userName",userName)
//            intent.putExtra("userPw",userPw)
//            intent.putExtra("userCompany",userCompany)
//            startActivity(intent)
        }

    }

    private fun insertUserInfo(
        userCode: String,
        userPw: String,
        userName: String,
        userEmail: String,
        company: String,
        userPhoneNumber: String,
        profileImg: String,
        position: String
    )
    {
        val client = OkHttpClient()

        val body = FormBody.Builder().add("userCode", userCode).add("userPw", userPw).add("userName", userName).add("userEmail", userEmail)
            .add("userCompany", company).add("userPhoneNumber", userPhoneNumber).add("userProfile", profileImg).add("position", position).build()

        val request = Request.Builder().url(REGISTER_URL).post(body).build()

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
                    //완료시 로그인화면으로 이동
                    runOnUiThread {
                    Toast.makeText(applicationContext, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()

                    }
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        })

    }

}