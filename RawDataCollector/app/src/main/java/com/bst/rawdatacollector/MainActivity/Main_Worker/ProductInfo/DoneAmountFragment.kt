package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.databinding.FragmentDoneAmountBinding


class DoneAmountFragment : Fragment()
{

    private lateinit var binding:FragmentDoneAmountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentDoneAmountBinding.inflate(layoutInflater)



        return binding.root
    }

}