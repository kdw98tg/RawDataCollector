package com.bst.rawdatacollector.MainActivity.Main_Manager.CreateNewWork

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.NewWork
import com.bst.rawdatacollector.UserData.UserData
import com.bst.rawdatacollector.databinding.ItemCreateWorkBinding
import java.time.LocalDate

class CreateNewWorkAdapter(private val context: Context, private val newWorkList: ArrayList<NewWork>) :
    RecyclerView.Adapter<CreateNewWorkAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateNewWorkAdapter.ViewHolder
    {
        val binding: ItemCreateWorkBinding = ItemCreateWorkBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreateNewWorkAdapter.ViewHolder, position: Int)
    {
        val newWork = newWorkList[position]
        holder.binding.requestUser.text = newWork.requestUser
        holder.binding.acceptUser.text = newWork.acceptUser
        holder.binding.equipment.text = newWork.equipment
        holder.binding.product.text = newWork.product
        holder.binding.amount.text = newWork.amount
        holder.binding.process.text = newWork.process
    }

    override fun getItemCount(): Int
    {
        return newWorkList.size
    }

    inner class ViewHolder(val binding: ItemCreateWorkBinding) : RecyclerView.ViewHolder(binding.root)
}