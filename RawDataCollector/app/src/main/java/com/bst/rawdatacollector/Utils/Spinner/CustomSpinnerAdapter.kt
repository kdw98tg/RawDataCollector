package com.bst.rawdatacollector.Utils.Spinner

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bst.rawdatacollector.R

//커스텀 SpinnerAdapter를 사용하기 위한 클래스
class CustomSpinnerAdapter(private val context: Context, private val list:ArrayList<String>?): BaseAdapter()
{

    private var inflater:LayoutInflater? = null
    private lateinit var text:String

    init{
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int
    {
        return list!!.size
    }

    override fun getItem(position: Int): Any
    {
        return list!![position]
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        //바깥 레이아웃 설정
        val convertView:View? = inflater?.inflate(R.layout.spinner_outer_view,parent,false)
        text = list!![position]
        (convertView?.findViewById<View>(R.id.spinner_inner_text) as TextView).text = text

        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        //안쪽 레이아웃 설정
        val convertView:View? = inflater?.inflate(R.layout.spinner_inner_view,parent,false)
        text = list!![position]
        (convertView?.findViewById<View>(R.id.spinner_text) as TextView).text = text

        return convertView
    }

    //고른 아이템을 리턴
    fun getItem():String{
        return text
    }

}