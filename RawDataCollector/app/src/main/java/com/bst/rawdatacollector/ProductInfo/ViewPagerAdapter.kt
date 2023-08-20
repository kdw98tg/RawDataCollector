package com.bst.rawdatacollector.ProductInfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager:FragmentManager, lifecycle:Lifecycle, private val amount:Int): FragmentStateAdapter(fragmentManager,lifecycle)
{

    //tabLayout 에서 설정한 페이지 수를 설정
    override fun getItemCount(): Int
    {
        return amount
    }

    override fun createFragment(position: Int): Fragment
    {
        return when (position)
        {
            0 ->
            {
                DoneAmountFragment()
            }

            1 ->
            {
                ProductErrorFragment()
            }

            else ->
            {
                MachineErrorFragment()
            }
        }
    }


}