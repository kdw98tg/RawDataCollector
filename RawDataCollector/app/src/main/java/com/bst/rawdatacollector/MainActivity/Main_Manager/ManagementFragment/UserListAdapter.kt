package com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.User
import com.bst.rawdatacollector.databinding.ItemWorkerBinding

class UserListAdapter(
    private val context: Context, private val workerList: ArrayList<User>,private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder
    {
        val binding = ItemWorkerBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int)
    {
        val worker = workerList[position]

        holder.binding.showMoreBtn.setOnClickListener {
            //itemEditCallback?.voidVoidDelegate()
            val moreTasks = MoreTasks(context,worker)
            moreTasks.show(fragmentManager, MoreTasks.TAG)
        }
    }

    override fun getItemCount(): Int
    {
        return workerList.size
    }

    inner class ViewHolder(val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root)
}