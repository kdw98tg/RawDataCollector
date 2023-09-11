package com.bst.rawdatacollector.MainActivity.Main_Manager.ProductInfoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.DataClass.Product
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.FragmentProductInfoBinding


class ProductInfoFragment : Fragment()
{

    private lateinit var binding: FragmentProductInfoBinding
    private lateinit var productAdapter:ProductInfoAdapter
    private lateinit var productList:ArrayList<Product>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProductInfoBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //init
        productList = ArrayList()
        productAdapter = ProductInfoAdapter(requireContext(),productList)

        selectProductList()

    }
    private fun selectProductList()
    {

    }

}