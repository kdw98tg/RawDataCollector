package com.bst.rawdatacollector.MainActivity.Main_Manager.ProductList_Manager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bst.rawdatacollector.databinding.FragmentProductListManagerBinding


class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListManagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectAllProducingLists()//모든 작업을 가져오는 함수

        //작업 추가로 넘어감
        binding.workDistributeFab.setOnClickListener{
            val intent:Intent = Intent()
            startActivity(intent)
        }

    }

    private fun selectAllProducingLists()
    {

    }
}