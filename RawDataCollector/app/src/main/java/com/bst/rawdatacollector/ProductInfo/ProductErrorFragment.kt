package com.bst.rawdatacollector.ProductInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.databinding.FragmentProductErrorBinding


class ProductErrorFragment : Fragment()
{
    private lateinit var binding:FragmentProductErrorBinding
    private var machineCount = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProductErrorBinding.inflate(layoutInflater)

        machineCount = 3

        return binding.root
    }

    fun getMachineErrorCount():Int
    {
        return machineCount
    }

}