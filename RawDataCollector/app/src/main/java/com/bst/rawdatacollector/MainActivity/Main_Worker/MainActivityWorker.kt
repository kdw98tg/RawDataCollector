package com.bst.rawdatacollector.MainActivity.Main_Worker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.CurStateFragment.CurStateFragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.FragmentHome.HomeFragment
import com.bst.rawdatacollector.MainActivity.Main_Worker.ProductListFragment.ProductListFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityMainBinding


class MainActivityWorker : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //시작 화면 세팅
        replaceFragment(HomeFragment())

        //bottom nav 세팅
        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId)
            {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.productList -> replaceFragment(ProductListFragment())
                R.id.more -> replaceFragment(CurStateFragment())

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