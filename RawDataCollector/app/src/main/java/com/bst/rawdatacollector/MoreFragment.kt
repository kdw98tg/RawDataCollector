package com.bst.rawdatacollector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.databinding.FragmentMoreBinding


class MoreFragment : Fragment()
{
    private lateinit var binding: FragmentMoreBinding
    override fun onCreateView(_inflater: LayoutInflater,
                              _container: ViewGroup?,
                              _savedInstanceState: Bundle?): View?
    {
        binding = FragmentMoreBinding.inflate(_inflater, _container, false)
        return binding.root
    }
}