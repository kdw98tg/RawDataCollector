package com.bst.rawdatacollector.MainActivity

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.Delegate.VoidStringDelegate
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ItemCalendarBinding
import java.time.LocalDate

class CalendarAdapter(
    private val context: Context,
    private var dayList: ArrayList<LocalDate?>
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private var curStateCallback: VoidStringDelegate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //날짜 변수에 담기
        val day = dayList[position]
        if (day == null) //day == null 이면 날짜 표시 하지 않기
        {
            holder.binding.dayText.text = ""
        } else  //그게 아니면 날짜 표시
        {
            holder.binding.dayText.text = day.dayOfMonth.toString()

            //현재 날짜 색상 칠하기
            if (day.monthValue == LocalDate.now().monthValue && day.year == LocalDate.now().year && day.dayOfMonth == LocalDate.now().dayOfMonth) //오늘의 년,월,일이 다 같을때
            {
                holder.binding.dayText.setBackgroundResource(R.drawable.today_circle)
            }


        }

        //데이터베이스에서 결과를 가져왔을때 오늘 완벽 출근을 했다면
        //해당날짜 Array를 만들어서 반복문 돌린다음에 표시하면됨

        //텍스트 색상 지정 (토,일)

        //데이터베이스에서 결과를 가져왔을때 오늘 완벽 출근을 했다면
        //해당날짜 Array를 만들어서 반복문 돌린다음에 표시하면됨

        //텍스트 색상 지정 (토,일)
        if ((position + 1) % 7 == 0) //토요일 이면
        {
            holder.binding.dayText.setTextColor(Color.BLUE)
        } else if (position == 0 || position % 7 == 0) //일요일 이면
        {
            holder.binding.dayText.setTextColor(Color.RED)
            // 오늘 날짜 색상 칠하기
        }


        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener {
            if (day != null) {
                val iYear = day.year //년
                val iMonth = day.monthValue //월
                val iDay = day.dayOfMonth //일
                val yearMonday =
                    iYear.toString() + "년 " + monthValueFormat(iMonth) + "월 " + dayValueFormat(iDay) + "일"
                val yearMonthDate =
                    iYear.toString() + "-" + monthValueFormat(iMonth) + "-" + dayValueFormat(iDay)
                if (curStateCallback != null) {
                    curStateCallback?.voidStringDelegate(yearMonthDate)
                }
                Toast.makeText(holder.itemView.context, yearMonday, Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class ViewHolder(val binding: ItemCalendarBinding) : RecyclerView.ViewHolder(binding.root)

    fun setCurStateCallback(_curStateCallback: VoidStringDelegate) {
        this.curStateCallback = _curStateCallback
    }

    private fun monthValueFormat(_month: Int): String? {
        return if (_month < 10) {
            "0$_month"
        } else {
            "" + _month
        }
    }

    private fun dayValueFormat(_day: Int): String? {
        return if (_day < 10) {
            "0$_day"
        } else {
            "" + _day
        }
    }
}
