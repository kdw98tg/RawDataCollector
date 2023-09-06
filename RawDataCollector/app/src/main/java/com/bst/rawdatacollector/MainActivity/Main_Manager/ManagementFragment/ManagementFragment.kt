package com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.Member
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.FragmentManagementBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ManagementFragment : Fragment() {
    private lateinit var binding: FragmentManagementBinding
    private lateinit var memberList: ArrayList<Member>
    private lateinit var memberAdapter: MemberAdapter

    companion object {
        private const val SELECT_MEMBER_URL =
            "http://kdw98tg.dothome.co.kr/RDC/Select_MembersInfo.php"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagementBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init
        memberList = ArrayList()
        memberAdapter = MemberAdapter(
            requireContext(),
            memberList,
            requireActivity().supportFragmentManager
        )//오류나면 의심해볼 구간

        binding.recyclerView.adapter = memberAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        selectMembers(UserData.getInstance(requireContext()).userCompany)


        //내꺼는 뺄지말지 정하기
        //인스타처럼할거임
        //selectUserList(UserData.getInstance(requireContext()).userCompany)//유저들 골라옴

    }

    private fun selectMembers(company: String) {
        val client: OkHttpClient = OkHttpClient()
        val body = FormBody.Builder().add("company", company).build()
        val request = Request.Builder().url(SELECT_MEMBER_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val result = response.body!!.string()
                    Log.d("멤버리스트", "onResponse: $result")
                    try {
                        val jsonObject = JSONObject(result)
                        val jsonArray = JSONArray(jsonObject.getString("results"))

                        for (i in 0 until jsonArray.length()) {
                            //JsonObject 해체 작업
                            val json = jsonArray.getJSONObject(i)
                            val memberCode = json.getString("user_code").toString()
                            val memberName = json.getString("name").toString()
                            val memberEmail = json.getString("email").toString()
                            val memberPhoneNumber = json.getString("phone_number").toString()
                            val memberProfileImg = json.getString("profile_img").toString()
                            val memberPosition = json.getString("position").toString()
                            val memberWage = json.getString("wage")

                            val member: Member = Member()
                            member.memberCode = memberCode
                            member.memberName = memberName
                            member.memberEmail = memberEmail
                            member.memberPhoneNumber = memberPhoneNumber
                            member.memberProfileImg = memberProfileImg
                            member.memberPosition = memberPosition
                            member.memberWage = memberWage

                            memberList.add(member)
                            activity?.runOnUiThread {
                                memberAdapter.notifyDataSetChanged()
                            }

                        }


                    } catch (e: Exception)//로그인 실패시
                    {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}