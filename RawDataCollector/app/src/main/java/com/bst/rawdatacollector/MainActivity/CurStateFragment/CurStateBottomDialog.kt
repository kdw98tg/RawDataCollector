package com.bst.rawdatacollector.MainActivity.CurStateFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.bst.rawdatacollector.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CurStateBottomDialog(
) :
    BottomSheetDialogFragment() {
    private var yearMonthDayText: TextView? = null
    private var date:String?=null

    companion object{
        const val TAG = "TAG"
    }

    override fun onCreateView(
        _inflater: LayoutInflater,
        _container: ViewGroup?,
        _savedInstance: Bundle?
    ): View {

        //EditText 입력창 inflate
        val viewGroup = _inflater.inflate(R.layout.dialog_state, _container, false) as ViewGroup
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        bindView(viewGroup)

        //set Adapter
        yearMonthDayText!!.text = date
        return viewGroup
    }

    private fun bindView(_view: View) {
        yearMonthDayText = _view.findViewById<TextView>(R.id.dayText)
    }

    fun setDayString(_date: String) {
        this.date = _date
    }


}