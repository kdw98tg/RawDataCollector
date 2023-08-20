package com.bst.rawdatacollector.MainActivity.Main_Manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityMainManagerBinding

class MainActivityManager : AppCompatActivity()
{
    private lateinit var binding:ActivityMainManagerBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}