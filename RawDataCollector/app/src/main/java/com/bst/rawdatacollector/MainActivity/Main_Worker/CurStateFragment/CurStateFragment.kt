package com.bst.rawdatacollector.MainActivity.Main_Worker.CurStateFragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bst.rawdatacollector.Delegate.VoidStringDelegate
import com.bst.rawdatacollector.MainActivity.Main_Worker.WorkHistory.WorkHistoryActivity
import com.bst.rawdatacollector.databinding.FragmentCurStateBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CurStateFragment : Fragment()
{

    private lateinit var binding: FragmentCurStateBinding
    private lateinit var adapter: CalendarAdapter

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreateView(_inflater: LayoutInflater, _container: ViewGroup?, _savedInstanceState: Bundle?): View
    {
        //프래그먼트 연결
        binding = FragmentCurStateBinding.inflate(layoutInflater)

        var selectedDate = LocalDate.now() //현재 날짜

        setMonthView() //화면 설정

        //날짜 선택시 이벤트
        adapter.setCurStateCallback(object : VoidStringDelegate
        {
            override fun voidStringDelegate(_data: String)
            {
                val intent = Intent(requireContext(), WorkHistoryActivity::class.java)
                intent.putExtra("selectedDate",_data)
                startActivity(intent)
            }
        })

        //이전달 클릭 이벤트
        binding.prevBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1) //오늘에서 월만 -1
            setMonthView()
        }

        //다음달 클릭 리스너
        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1) //오늘에서 월만 +1
            setMonthView()
        }
        return binding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setMonthView() //화면 설정
    {


        val selectedDate = LocalDate.now()
        //년월 텍스트뷰 셋팅
        binding.monthYearText.text = (monthYearFromDate(selectedDate))

        //년월 텍스트 뷰 셋팅
        val dayList: ArrayList<LocalDate?> = daysInMonthArray(selectedDate)

        //어뎁터 데이터 적용
        adapter = CalendarAdapter(requireContext(), dayList)

        //레이아웃 설정 (열이 7개)
        val manager: RecyclerView.LayoutManager = GridLayoutManager(getContext(), 7)
        //StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.HORIZONTAL);
        //레이아웃 적용
        binding.recyclerView.layoutManager = manager

        //어댑터 적용
        binding.recyclerView.adapter = adapter
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun monthYearFromDate(_date: LocalDate): String? //날짜 타입 설정/(4월 20일)
    {
        val formatter = DateTimeFormatter.ofPattern("MM월 yyyy")
        return _date.format(formatter) //포멧에 맞게 String을 반환해주는 함수
    }

    //날짜 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun daysInMonthArray(_date: LocalDate): ArrayList<LocalDate?>
    {


        val selectedDate = LocalDate.now()
        val dayList = ArrayList<LocalDate?>()
        val yearMonth = YearMonth.from(_date)

        //해당 월 마지막 날짜 가져오기 (ex 28,30,31)
        val lastDay = yearMonth.lengthOfMonth()

        //해당 월의 첫 번째 날 가져오기(예 4월1일)
        val firstDay: LocalDate = selectedDate.withDayOfMonth(1)

        //첫번째 날 요일 가져오기(월:1 일 : 7)
        val dayOfWeek = firstDay.dayOfWeek.value

        //날짜 생성
        for (i in 0..41)
        {
            if (i <= dayOfWeek || i > lastDay + dayOfWeek)
            {
                dayList.add(null)
            }
            else
            {
                dayList.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek))
            }
        }
        return dayList
    }
}