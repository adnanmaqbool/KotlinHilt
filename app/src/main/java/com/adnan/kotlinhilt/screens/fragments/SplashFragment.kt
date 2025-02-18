package com.adnan.kotlinhilt.screens.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.adnan.kotlinhilt.databinding.FragmentSplashBinding
import com.adnan.kotlinhilt.navigation.AppNavigator
import com.adnan.kotlinhilt.services.RunningService
import com.adnan.kotlinhilt.session.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashBinding.inflate(inflater).also { _binding = it }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnStart?.setOnClickListener {

            Intent(requireActivity().applicationContext, RunningService::class.java).also {
                it.action = RunningService.ServiceState.STARTED.name
                requireActivity().applicationContext.startService(it)
            }

        }

        binding?.btnEnd?.setOnClickListener {

            Intent(requireActivity().applicationContext, RunningService::class.java).also {
                it.action = RunningService.ServiceState.STOPPED.name
                requireActivity().applicationContext.startService(it)
            }

        }
        /*lifecycleScope.launch {
            delay(2000) // 2 seconds delay
            // Code to execute after delay
            AppNavigator.navigateToHome()
        }*/

    }


}