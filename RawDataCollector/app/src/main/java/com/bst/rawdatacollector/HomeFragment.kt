package com.bst.rawdatacollector

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.databinding.FragmentHomeBinding
import java.time.LocalDate


class HomeFragment : Fragment()
{
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            binding.dayText.text = LocalDate.now().toString()
        }


        //출근
        binding.attendanceBtn.setOnClickListener{

        }
        //퇴근
        binding.attendanceBtn.setOnClickListener {

        }

        return binding.root
    }

}