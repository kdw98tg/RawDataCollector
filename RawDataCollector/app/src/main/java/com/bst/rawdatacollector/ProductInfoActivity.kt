package com.bst.rawdatacollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding

class ProductInfoActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityProductInfoBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}