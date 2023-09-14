package com.bst.rawdatacollector.MainActivity.Main_Manager.ProductInfoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityProductInfoBinding

class ProductInfoActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityProductInfoBinding

    private var productCode:String?=null
    private var productName:String?=null
    private var productImg:String?=null
    private var customer:String?=null
    private var requestAmount:Int?=null
    private var doneAmount:Int?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productCode = intent.getStringExtra("productCode")
        productName = intent.getStringExtra("productName")
        productImg = intent.getStringExtra("productImg")
        customer = intent.getStringExtra("customer")

    }
}