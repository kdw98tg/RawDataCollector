package com.bst.rawdatacollector.MainActivity.Main_Manager.ToolsInfoFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.Tool
import com.bst.rawdatacollector.databinding.ItemToolListBinding

class ToolInfoAdapter(private val context: Context, private val toolList: ArrayList<Tool>) : RecyclerView.Adapter<ToolInfoAdapter.ViewHolder>()
{

    private var toolInfoClickedListener:ToolInfoClickedListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolInfoAdapter.ViewHolder
    {
        val binding:ItemToolListBinding = ItemToolListBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToolInfoAdapter.ViewHolder, position: Int)
    {
        val tool:Tool = toolList[position]
        holder.binding.toolCode.text = tool.toolCode

        holder.binding.toolInfoLayout.setOnClickListener{
            toolInfoClickedListener?.onClicked()
        }
    }

    override fun getItemCount(): Int
    {
        return toolList.size
    }

    fun setToolInfoClickedListener(_toolInfoClickedListener:ToolInfoClickedListener)
    {
        toolInfoClickedListener = _toolInfoClickedListener
    }
    interface ToolInfoClickedListener
    {
        fun onClicked()
    }
    inner class ViewHolder(val binding: ItemToolListBinding) : RecyclerView.ViewHolder(binding.root)
}

