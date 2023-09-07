package com.bst.rawdatacollector.MainActivity.Main_Manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.MainActivity.Main_Manager.AllProjectListsFragment.AllProductListFragment
import com.bst.rawdatacollector.MainActivity.Main_Manager.ManagementFragment.ManagementFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityMainManagerBinding

class MainActivityManager : AppCompatActivity()
{
    private lateinit var binding: ActivityMainManagerBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //시작 화면 세팅
        replaceFragment(ManagementFragment())

        //bottom nav 세팅
        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId)
            {
                R.id.manage -> replaceFragment(ManagementFragment())
                R.id.productList -> replaceFragment(AllProductListFragment())
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