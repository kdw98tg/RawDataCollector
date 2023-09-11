package com.bst.rawdatacollector.MainActivity.Main_Manager.EquipmentInfoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityEquipmentInfoBinding

class EquipmentInfoActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityEquipmentInfoBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipmentInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}