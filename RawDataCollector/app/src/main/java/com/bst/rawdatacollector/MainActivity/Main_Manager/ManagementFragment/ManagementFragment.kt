package com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.DataClass.User
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.FragmentManagementBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ManagementFragment : Fragment()
{
    private lateinit var binding: FragmentManagementBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var userListAdapter: UserListAdapter

    companion object
    {
        private const val SELECT_USER_URL = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentManagementBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        //init
        userList = ArrayList()
        userListAdapter = UserListAdapter(requireContext(), userList, requireActivity().supportFragmentManager)//오류나면 의심해볼 구간

        binding.recyclerView.adapter = userListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val user1 = User("1")
        userList.add(user1)
        userListAdapter.notifyDataSetChanged()


        //내꺼는 뺄지말지 정하기
        //인스타처럼할거임
        //selectUserList(UserData.getInstance(requireContext()).userCompany)//유저들 골라옴

    }

    private fun selectUserList(company: String)
    {
        val client: OkHttpClient = OkHttpClient()
        val body = FormBody.Builder().add("company", company).build()
        val request = Request.Builder().url(SELECT_USER_URL).post(body).build()

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

                }
            }
        })
    }
}