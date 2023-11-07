package com.bst.rawdatacollector.UserInfo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.Utils.Utils.URL.URLManager
import com.bst.rawdatacollector.databinding.ActivityUserInfoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.io.IOException

class UserInfoActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var userData: UserData

    companion object
    {
        private const val INSERT_PROFILE_URL = "${URLManager.PHP_URL}Insert_UserProfileImg.php/"
        private const val SELECT_USER_PROFILE_URL = "${URLManager.PHP_URL}Select_UserProfile.php"
        //여기에는 다른 URL이 들어와야함
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        userData = UserData.getInstance(this@UserInfoActivity)

        //Image viewer
        selectUserProfileImg()

        //viewInit
        viewInit()

        //이미지 변경
        binding.userProfileImg.setOnClickListener {
            ImagePicker.with(this@UserInfoActivity).crop() //Crop image(Optional), Check Customization for more option
                .compress(1024) //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080) //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK)
        {
            val uri = data?.data
            val filePath = uri!!.path
            binding.userProfileImg.setImageURI(uri)//이미지 설정

            Log.d("113", "onActivityResult: $uri")

            insertUserProfileImg(filePath.toString())
        }
    }

    private fun insertUserProfileImg(path: String)
    {
        //creating a file
        val file = File(path)
        val userCode: String = UserData.getInstance(this@UserInfoActivity).userCode
        val userCompany: String = UserData.getInstance(this@UserInfoActivity).userCompany
        //TODO 파일 이름 바꾸기
        val fileName: String = userCode.replace("@", "").replace(".", "")

        //val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val requestBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        Log.d("파일경로", "$file")

        val body: MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
        Log.d("파일이름", "testRetrofit: $fileName")


        //gson builder
        //val gson: Gson = GsonBuilder().setLenient().create()

        //creating retrofit object
        val retrofit = Retrofit.Builder().baseUrl(INSERT_PROFILE_URL).addConverterFactory(ScalarsConverterFactory.create()).build()

        //creating our api
        val server = retrofit.create(RetrofitInterFace::class.java)

        //  파일, 사용자 아이디, 파일 이름
        Log.d("112233", "insertUserProfileImg: $userCode")
        server.postRequest(userCode,  body).enqueue(object : Callback<String>
        {
            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if (response.isSuccessful)
                {
                    Toast.makeText(applicationContext, "File Uploaded Successfully...", Toast.LENGTH_SHORT).show()

                    Log.d("레트로핏 결과2", "" + response.body().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable)
            {
                Log.d("레트로핏 결과1", "onFailure: ${t.message}")
            }
        })
    }

    private fun selectUserProfileImg()
    {
        val client: OkHttpClient = OkHttpClient()
        val body = FormBody.Builder().add("userCode", UserData.getInstance(this).userCode).build()
        val request = Request.Builder().url(SELECT_USER_PROFILE_URL).post(body).build()
        client.newCall(request).enqueue(object : okhttp3.Callback
        {
            override fun onFailure(call: okhttp3.Call, e: IOException)
            {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response)
            {
                if (response.isSuccessful)
                {
                    try
                    {
                        val jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        val json: JSONObject = jsonArray.getJSONObject(0)
                        val profile = json.getString("profile_img")

                        runOnUiThread {
                            viewUserProfile(profile)
                        }
                    }
                    catch (_e: Exception)
                    {
                        _e.printStackTrace()
                    }

                }
            }

        })
    }

    private fun viewInit()
    {
        binding.nameText.text = userData.userName
        binding.company.text = userData.userCompany
        binding.userCode.text = userData.userCode
        binding.position.text = userData.userPosition
        binding.phoneNumber.text = userData.userPhoneNumber
    }

    private fun viewUserProfile(imageUrl: String)
    {
        val userProfileUrl = URLManager.PHP_USER_IMAGE_URL + imageUrl + ".jpg"
        Glide.with(this).load(userProfileUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.userProfileImg)
    }

    private interface RetrofitInterFace
    {
        //api 를 관리해 주는 인터페이스
        //프로필 이미지 보내기
        @Multipart
        @POST("html/Insert_UserProfileImg.php")
        fun postRequest(
            @Part("userCode") userId: String,  @Part imageFile: MultipartBody.Part
        ): Call<String>
    }
}