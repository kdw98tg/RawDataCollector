package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolInfoActivity

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ToolUses
import com.bst.rawdatacollector.databinding.ItemToolInfoBinding
import java.time.LocalDate

class ToolInfoAdapter(private val context: Context, private val toolInfoList:ArrayList<ToolUses>): RecyclerView.Adapter<ToolInfoAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding:ItemToolInfoBinding = ItemToolInfoBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return toolInfoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val toolInfo = toolInfoList[position]
        holder.binding.dateText.text = toolInfo.date
        holder.binding.serialNumberText.text = toolInfo.serialNumber
        holder.binding.equipmentText.text = toolInfo.usesEquipment
        holder.binding.usesAmountText.text = toolInfo.usesAmount
    }


    inner class ViewHolder(val binding:ItemToolInfoBinding):RecyclerView.ViewHolder(binding.root)

}