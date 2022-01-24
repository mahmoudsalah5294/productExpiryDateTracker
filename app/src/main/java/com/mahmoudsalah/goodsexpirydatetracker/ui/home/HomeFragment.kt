package com.mahmoudsalah.goodsexpirydatetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepo
import com.mahmoudsalah.goodsexpirydatetracker.data.local.LocalRepoInterface
import com.mahmoudsalah.goodsexpirydatetracker.data.local.ProductsDao
import com.mahmoudsalah.goodsexpirydatetracker.data.local.ProductsDataBase
import com.mahmoudsalah.goodsexpirydatetracker.databinding.FragmentHomeBinding
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var database:ProductsDao
    private lateinit var repo:LocalRepoInterface

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = ProductsDataBase.getProductsData(requireContext()).productsDao()
        repo = LocalRepo(database)
        val factory = HomeViewModelFactory(repo)
        homeViewModel =
            ViewModelProvider(this,factory).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.getProducts()

        observeViewModel(homeViewModel)

        return root
    }

    private fun observeViewModel(homeViewModel: HomeViewModel) {
        homeViewModel.productLiveData.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    private fun setData(productsList: List<ProductsModel>) {
        if (productsList.size >3) {
            binding.homeRecyclerView.apply {
                this.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
                adapter = ProductsRecyclerAdapter(productsList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}