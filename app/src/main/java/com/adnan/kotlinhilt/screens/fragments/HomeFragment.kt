package com.adnan.kotlinhilt.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnan.kotlinhilt.adapter.CategoryListAdapter
import com.adnan.kotlinhilt.config.GlobalConfig
import com.adnan.kotlinhilt.databinding.FragmentHomeBinding
import com.adnan.kotlinhilt.interfaces.AdapterItemClick
import com.adnan.kotlinhilt.model.responseModel.CategoryItem
import com.adnan.kotlinhilt.navigation.AppNavigator
import com.adnan.kotlinhilt.session.SessionManager
import com.adnan.kotlinhilt.showErrorMsg
import com.adnan.kotlinhilt.showSuccessMsg
import com.adnan.kotlinhilt.viewmodel.HomeViewModel
import com.adnan.kotlinhilt.event.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var globalConfig: GlobalConfig
    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        homeViewModel.listApi(globalConfig.CAT_ID)
    }

    private fun setupRecyclerView() {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            categoryListAdapter = CategoryListAdapter(arrayListOf(), object : AdapterItemClick {
                override fun onItemClick(customObject: Any, position: Int) {
                    requireContext().showSuccessMsg("Show detail for category = ${(customObject as CategoryItem).Id}")

                    AppNavigator.navigateToSetting()
                }
            })
            adapter = categoryListAdapter
        }
    }

    private fun observeViewModel() {
        homeViewModel.getIsLoading().observe(viewLifecycleOwner, EventObserver { isLoading ->
            if (isLoading) showDialog() else hideDialog()
        })

        homeViewModel.categoryListResponse.observe(viewLifecycleOwner, EventObserver { response ->
            response?.let { category ->
                if (category.status == true)
                    categoryListAdapter.updateData(category.categoryList)
                else
                    categoryListAdapter.updateData(arrayListOf())
            } ?: requireContext().showErrorMsg("Failed to load categories")
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


