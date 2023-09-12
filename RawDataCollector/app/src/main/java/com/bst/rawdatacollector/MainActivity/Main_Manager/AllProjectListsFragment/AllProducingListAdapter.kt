package com.bst.rawdatacollector.MainActivity.Main_Manager.AllProjectListsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.DataClass.Producing
import com.bst.rawdatacollector.databinding.ItemProducelistBinding

class AllProducingListAdapter(
    private val context: Context, private val allProducingList:ArrayList<Producing>
): RecyclerView.Adapter<AllProducingListAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding:ItemProducelistBinding = ItemProducelistBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val producing:Producing = allProducingList[position]

        holder.binding.productNameText.text = producing.productName//제품명
        holder.binding.productCodeText.text = producing.productCode//품번
        holder.binding.requestName.text = producing.requestName//생산자 명
        holder.binding.acceptName.text = producing.acceptName//생산자 명
        holder.binding.processName.text = producing.process

//        if (UserData.getInstance(context).isWorking)//작업중일때만 workingText 표시할거임
//        {
//            holder.binding.workingText.visibility = View.VISIBLE
//        }
//        else
//        {
//            holder.binding.workingText.visibility = View.GONE
//        }
    }

    override fun getItemCount(): Int
    {
        return allProducingList.size
    }
    inner class ViewHolder(val binding:ItemProducelistBinding):RecyclerView.ViewHolder(binding.root)
}
