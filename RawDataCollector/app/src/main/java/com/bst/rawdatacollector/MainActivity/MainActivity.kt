package com.bst.rawdatacollector.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bst.rawdatacollector.MainActivity.CurStateFragment.CurStateFragment
import com.bst.rawdatacollector.MainActivity.FragmentHome.HomeFragment
import com.bst.rawdatacollector.MainActivity.ProductListFragment.ProductListFragment
import com.bst.rawdatacollector.R
import com.bst.rawdatacollector.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

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