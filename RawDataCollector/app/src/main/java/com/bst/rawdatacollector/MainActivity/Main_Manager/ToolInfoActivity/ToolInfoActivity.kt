package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolInfoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityToolInfoBinding

class ToolInfoActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityToolInfoBinding
    private var toolCode: String? = null
    private var toolName: String? = null
    private var toolSerialNumber: String? = null
    private var toolImage: String? = null
    private var usesAmount: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityToolInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolCode = intent.getStringExtra("toolCode")
        toolName = intent.getStringExtra("toolName")
        toolSerialNumber = intent.getStringExtra("toolSerialNumber")
        toolImage = intent.getStringExtra("toolImage")
        usesAmount = intent.getStringExtra("usesAmount")

        binding.toolCodeText.text = toolCode
        binding.toolNameText.text = toolName
        binding.toolSerialNumberText.text = toolSerialNumber
        binding.toolUsesAmountText.text = usesAmount


    }
}