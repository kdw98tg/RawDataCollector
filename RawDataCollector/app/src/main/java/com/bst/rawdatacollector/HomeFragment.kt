package com.bst.rawdatacollector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.databinding.FragmentHomeBinding


class HomeFragment : Fragment()
{
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(_inflater: LayoutInflater,
                              _container: ViewGroup?,
                              _savedInstanceState: Bundle?): View?
    {
        binding = FragmentHomeBinding.inflate(_inflater, _container, false)
        return binding.root
    }
}