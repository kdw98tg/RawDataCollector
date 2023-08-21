package com.bst.rawdatacollector.MainActivity.Main_Worker.WorkHistory

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.WorkList
import com.bst.rawdatacollector.databinding.ItemWorkHistoryBinding

class WorkHistoryAdapter(private val context: Context, private val workHistoryList: ArrayList<WorkList>) :
    RecyclerView.Adapter<WorkHistoryAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding:ItemWorkHistoryBinding = ItemWorkHistoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return workHistoryList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val workHistory = workHistoryList[position]
        holder.binding.workHistoryText.text = workHistory.workProcess
        holder.binding.workAmountText.text = "${workHistory.workAmount} 개"
    }

    inner class ViewHolder(val binding: ItemWorkHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}