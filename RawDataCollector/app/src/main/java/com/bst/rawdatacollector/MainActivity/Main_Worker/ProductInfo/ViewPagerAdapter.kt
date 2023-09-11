package com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.DoneAmount.ProductResultFragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductInfo.Equipment.EquipmentErrorFragment

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
                ProductResultFragment()
            }

            else ->
            {
                EquipmentErrorFragment()
            }
        }
    }


}