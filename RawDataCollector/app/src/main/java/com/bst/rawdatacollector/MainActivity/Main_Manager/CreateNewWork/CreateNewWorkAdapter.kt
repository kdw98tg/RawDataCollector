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

class CreateNewWorkAdapter(private val context: Context, private val newWorkList:ArrayList<NewWork>): RecyclerView.Adapter<CreateNewWorkAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateNewWorkAdapter.ViewHolder
    {
        val binding:ItemCreateWorkBinding = ItemCreateWorkBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CreateNewWorkAdapter.ViewHolder, position: Int)
    {
        val newWork = newWorkList[position]
        holder.binding.requestUser.text = UserData.getInstance(context).userName
        holder.binding.acceptUser.text = "1"
        holder.binding.workDate.text = getCurDate().toString()
        holder.binding.equipment.text = "1"
        holder.binding.product.text = "1"
        holder.binding.amount.text = "1"
        holder.binding.process.text = "1"
    }

    override fun getItemCount(): Int
    {
        return newWorkList.size
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurDate(): LocalDate
    {
        return LocalDate.now()
    }
    inner class ViewHolder(val binding:ItemCreateWorkBinding):RecyclerView.ViewHolder(binding.root)
}