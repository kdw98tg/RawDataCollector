package com.bst.rawdatacollector.UserInfo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.ActivityUserInfoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.REQUEST_CODE
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

class UserInfoActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var userData: UserData

    companion object
    {
        private const val INSERT_PROFILE_URL = "http://kdw98tg.dothome.co.kr/RDC/Insert_UserProfileImg.php/"
        private const val PIC_LOAD_URL = "http://kdw98tg.dothome.co.kr/htmlBSTABC123"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        userData = UserData.getInstance(this@UserInfoActivity)

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
        val fileName: String = userCompany + userCode.replace("@", "").replace(".", "")

        //val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val requestBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        Log.d("파일경로", "$file")

        val body: MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
        Log.d("파일이름", "testRetrofit: $fileName")


        //gson builder
        val gson: Gson = GsonBuilder().setLenient().create()

        //creating retrofit object
        val retrofit = Retrofit.Builder().baseUrl(INSERT_PROFILE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()

        //creating our api
        val server = retrofit.create(RetrofitInterFace::class.java)

        //  파일, 사용자 아이디, 파일 이름
        Log.d("112233", "insertUserProfileImg: $userCode")
        server.postRequest(userCode, "html$fileName", body).enqueue(object : Callback<String>
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

    private fun viewInit()
    {
        binding.nameText.text = userData.userName
        binding.company.text = userData.userCompany
        binding.userCode.text = userData.userCode
        binding.position.text = userData.userPosition
        binding.position.text = userData.userPhoneNumber

        Glide.with(this).load(PIC_LOAD_URL).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.userProfileImg)
    }

    private interface RetrofitInterFace
    {
        //api 를 관리해 주는 인터페이스
        //프로필 이미지 보내기
        @Multipart
        @POST("html/Insert_UserProfileImg.php")
        fun postRequest(
            @Part("userCode") userId: String, @Part("serverDir") serverDir: String, @Part imageFile: MultipartBody.Part
        ): Call<String>
    }
}