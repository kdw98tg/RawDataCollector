package com.bst.rawdatacollector.MainActivity.Main_Engineer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.MainActivity.Main_Manager.AllProjectListsFragment.AllProductListFragment
import com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment.ManagementFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityMainEngineerBinding

class MainActivityEngineer : AppCompatActivity() {

    private lateinit var binding:ActivityMainEngineerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainEngineerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(ManagementFragment())

        //bottom nav 세팅
        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId)
            {
                //manager 과 비슷하게 동작
                //manager 에 있는 프래그먼트를 돌려쓸거임
                R.id.manage -> replaceFragment(ManagementFragment())//공유를 해야할까 말까 고민해보기
                R.id.productList -> replaceFragment(AllProductListFragment())//
                //툴, 장비 관리할 수 있도록 만들기
                //관리자 명단, 프로덕트 명단, 툴명단, 기계명단 이렇게 4가지 프래그먼트로 구성하기
//                R.id.more -> replaceFragment(CurStateFragment())

                else ->
                {

                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}