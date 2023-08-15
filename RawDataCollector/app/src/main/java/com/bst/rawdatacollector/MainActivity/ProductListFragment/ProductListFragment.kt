package com.bst.rawdatacollector.MainActivity.ProductListFragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.rawdatacollector.Delegate.VoidVoidDelegate
import com.bst.rawdatacollector.DataClass.Product
import com.bst.rawdatacollector.Product.ProductAdapter
import com.bst.rawdatacollector.Product.ProductInfoActivity
import com.bst.rawdatacollector.databinding.FragmentProductListBinding
import java.time.LocalDate


class ProductListFragment : Fragment()
{
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: ArrayList<Product>
    override fun onCreateView(_inflater: LayoutInflater, _container: ViewGroup?, _savedInstanceState: Bundle?): View?
    {
        binding = FragmentProductListBinding.inflate(_inflater, _container, false)
        productList = ArrayList()
        setTestList()
        productAdapter = ProductAdapter(requireContext(), productList)//kotlin 은 requireContext()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            binding.dayText.text = LocalDate.now().toString()//오늘 날짜를 나타냄
        }


        binding.recyclerView.adapter = productAdapter//set adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager//setLayoutManger

        productAdapter.setProductItemTouchCallback(object : VoidVoidDelegate
        {
            override fun voidVoidDelegate()
            {
                val intent: Intent = Intent(requireContext(), ProductInfoActivity::class.java)
                startActivity(intent)
            }

        })

        return binding.root
    }

    private fun setTestList()
    {
        val product1: Product = Product("제품 A", "ABC-123", "홍길동")
        val product2: Product = Product("제품 A", "ABC-123", "홍길동")
        val product3: Product = Product("제품 A", "ABC-123", "홍길동")
        val product4: Product = Product("제품 A", "ABC-123", "홍길동")
        val product5: Product = Product("제품 A", "ABC-123", "홍길동")
        val product6: Product = Product("제품 A", "ABC-123", "홍길동")
        val product7: Product = Product("제품 A", "ABC-123", "홍길동")
        val product8: Product = Product("제품 A", "ABC-123", "홍길동")
        val product9: Product = Product("제품 A", "ABC-123", "홍길동")

        productList.add(product1)
        productList.add(product2)
        productList.add(product3)
        productList.add(product4)
        productList.add(product5)
        productList.add(product6)
        productList.add(product7)
        productList.add(product8)
        productList.add(product9)
    }
}