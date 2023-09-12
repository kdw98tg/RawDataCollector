package com.bst.rawdatacollector.MainActivity.Main_Manager.EquipmentListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.Equipment
import com.bst.rawdatacollector.databinding.ItemEquipmentListBinding

class EquipmentListAdapter(private val context: Context, private val equipmentList: ArrayList<Equipment>) :
    RecyclerView.Adapter<EquipmentListAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding:ItemEquipmentListBinding = ItemEquipmentListBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return equipmentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val equipment = equipmentList[position]


    }
    inner class ViewHolder(val binding:ItemEquipmentListBinding):RecyclerView.ViewHolder(binding.root)

}