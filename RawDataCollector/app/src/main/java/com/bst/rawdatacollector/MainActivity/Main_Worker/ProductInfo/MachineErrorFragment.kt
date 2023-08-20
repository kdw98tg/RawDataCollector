package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.databinding.FragmentMachineErrorBinding


class MachineErrorFragment : Fragment()
{
    private lateinit var binding:FragmentMachineErrorBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMachineErrorBinding.inflate(layoutInflater)

        return binding.root
    }


}