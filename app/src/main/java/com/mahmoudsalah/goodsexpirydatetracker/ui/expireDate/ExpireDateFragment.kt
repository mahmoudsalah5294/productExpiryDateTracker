package com.mahmoudsalah.goodsexpirydatetracker.ui.expireDate

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
import com.mahmoudsalah.goodsexpirydatetracker.databinding.FragmentExpireDateBinding
import com.mahmoudsalah.goodsexpirydatetracker.model.ProductsModel
import com.mahmoudsalah.goodsexpirydatetracker.ui.home.ProductsRecyclerAdapter

class ExpireDateFragment : Fragment() {

    private lateinit var expireDateViewModel: ExpireDateViewModel
    private var _binding: FragmentExpireDateBinding? = null
    private lateinit var database: ProductsDao
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
        val factory = ExpiryViewModelFactory(repo)
        expireDateViewModel =
            ViewModelProvider(this, factory).get(ExpireDateViewModel::class.java)

        _binding = FragmentExpireDateBinding.inflate(inflater, container, false)

        expireDateViewModel.getExpiryProducts()
        observeViewModel(expireDateViewModel)
        return binding.root
    }

    private fun observeViewModel(expireDateViewModel: ExpireDateViewModel) {
        expireDateViewModel.expiryProductLiveData.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    private fun setData(expiryProductList: List<ProductsModel>) {
        binding.expiryRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(
                requireContext(),LinearLayoutManager.VERTICAL,false
            )
            adapter = ProductsRecyclerAdapter(expiryProductList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}