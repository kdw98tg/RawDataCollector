package com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.Member
import com.bst.rawdatacollector.databinding.ItemWorkerBinding

class MemberAdapter(
    private val context: Context, private val memberList: ArrayList<Member>, private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<MemberAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder
    {
        val binding = ItemWorkerBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int)
    {
        val member = memberList[position]

        holder.binding.collaboratorNameText.text=member.memberName
        holder.binding.collaboratorEmailText.text = member.memberEmail
        holder.binding.positionText.text = member.memberPosition

        holder.binding.moreTaskLayout.setOnClickListener {
            val moreTasks = MoreTasks(context,member)
            moreTasks.show(fragmentManager, MoreTasks.TAG)
        }
    }


    override fun getItemCount(): Int
    {
        return memberList.size
    }

    inner class ViewHolder(val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root)
}