package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolsInfoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.FragmentToolInfoBinding

class ToolsInfoFragment : Fragment() {

    private lateinit var binding:FragmentToolInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentToolInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}