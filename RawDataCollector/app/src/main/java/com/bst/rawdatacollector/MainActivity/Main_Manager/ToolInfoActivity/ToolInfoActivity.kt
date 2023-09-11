package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolInfoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityToolInfoBinding

class ToolInfoActivity : AppCompatActivity()
{

    private lateinit var binding:ActivityToolInfoBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityToolInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}