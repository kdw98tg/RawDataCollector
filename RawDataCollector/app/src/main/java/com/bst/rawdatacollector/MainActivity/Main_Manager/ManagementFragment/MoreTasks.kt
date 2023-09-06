package com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.bst.rawdatacollector.DataClass.Member
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.DialogBottomMoreBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MoreTasks(private val context: Context, private val collaborator: Member?) : BottomSheetDialogFragment()
{
    private lateinit var client:OkHttpClient
    companion object
    {
        const val TAG = "MoreTasks"
        private const val DELETE_COLLABORATOR_URL = ""
        private const val UPDATE_AUTHORITY_URL = ""
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        //다이얼로그 스타일 지정
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }
    override fun onCreateView(_inflater: LayoutInflater, _container: ViewGroup?, _savedInstance: Bundle?): View
    {

        //EditText 입력창 증강
        val binding = DialogBottomMoreBinding.inflate(LayoutInflater.from(context), _container, false)
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)

        //init
        client = OkHttpClient()

        //adapter 로 콜백 넘김
        binding.callBtn.setOnClickListener {
//            val phoneNumber = collaborator?.collaboratorPhoneNumber
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:${phoneNumber}"))
//            startActivity(intent)
        }

        //collaborator 권한 수정하기
        binding.deleteBtn.setOnClickListener {
//            if (UserData.getInstance(context).getUserAuthority() <= UserData.MASTER_AUTHORITY)
//            {
//                val collaboratorId = collaborator?.collaboratorId.toString().trim()
//                val projectId = UserData.getInstance(context).getCurProjectId().toString().trim()
//                deleteDialog(context, projectId,collaboratorId)
//            }
//            else
//            {
//                authorityErrorDialog(context)
//            }
        }

        //collaborator 삭제하기
        binding.editBtn.setOnClickListener {
//            if (UserData.getInstance(context).getUserAuthority() <= UserData.MASTER_AUTHORITY)
//            {
//                //TODO 권한 설정할 수 있는 dropbox 만들기
//                val collaboratorId = collaborator?.collaboratorId.toString().trim()
//                val projectId = UserData.getInstance(context).getCurProjectId().toString().trim()
//                editDialog(context, collaboratorId, projectId)
//            }
//            else
//            {
//                authorityErrorDialog(context)
//
//            }
        }


        return binding.root
    }

    private fun authorityErrorDialog(_context: Context)
    {
        val builder = AlertDialog.Builder(_context)
        builder.setTitle("Error Message").setMessage("권한이 없습니다.")
        builder.setPositiveButton("확인") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteDialog(_context: Context, _projectId: String, _collaboratorId: String)
    {
        val builder = AlertDialog.Builder(_context)
        builder.setTitle("Error Message").setMessage("정말 삭제 하시겠습니까?")
        builder.setPositiveButton("확인") { dialogInterface, i ->
            deleteCollaborator(_projectId, _collaboratorId)
        }
        builder.setNegativeButton("취소") { dialogInterface, i ->

        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun editDialog(_context: Context, _projectId: String, _collaboratorId: String)
    {
        val builder = AlertDialog.Builder(_context)
        builder.setTitle("권한 수정").setMessage("수정하실랍니까?")
        builder.setPositiveButton("확인") { dialogInterface, i ->
            updateAuthority(_projectId, _collaboratorId)

        }
        builder.setNegativeButton("취소") { dialogInterface, i ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun updateAuthority(_projectId: String, _collaboratorId: String)
    {
        val body = FormBody.Builder().add("projectId", _projectId).add("collaboratorId", _collaboratorId).build()

        val request = Request.Builder().url(UPDATE_AUTHORITY_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback
        {

            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                //Toast.makeText(context, "잠시 후 다시 시도해 주세요", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response)
            {
                Log.d(TAG, "onResponse: $response")
                //Toast.makeText(context, "갱신 성공!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteCollaborator(_projectId: String, _collaboratorId: String)
    {
        val body = FormBody.Builder().add("projectId", _projectId).add("collaboratorId", _collaboratorId).build()

        val request = Request.Builder().url(DELETE_COLLABORATOR_URL).post(body).build()

        client.newCall(request).enqueue(object : Callback
        {

            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace()
                //Toast.makeText(context, "잠시 후 다시 시도해 주세요", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response)
            {
                Log.d(TAG, "onResponse1122: $response")
                //Toast.makeText(context, "갱신 성공!", Toast.LENGTH_SHORT).show()
            }
        })

    }

}