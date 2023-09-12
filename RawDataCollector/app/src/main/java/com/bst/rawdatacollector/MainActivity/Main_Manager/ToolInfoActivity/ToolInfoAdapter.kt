package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolInfoActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.ToolUses
import com.bst.rawdatacollector.databinding.ItemToolInfoBinding

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

    }

    inner class ViewHolder(val binding:ItemToolInfoBinding):RecyclerView.ViewHolder(binding.root)

}