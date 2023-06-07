package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentOnBoardingBinding
import com.capstone.bangkit.calendivity.presentation.di.OnboardingViewModel
import com.capstone.bangkit.calendivity.presentation.utils.OnboardingEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private val viewModel by viewModels<OnboardingViewModel>()
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // get user preferences data to local
        getCachedOnboarding()

        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateViewOnEvent(
        event: OnboardingEvent
    ) {
        when (event) {
            is OnboardingEvent.OnboardingCachedSuccess -> {
            }
            is OnboardingEvent.CachedonboardingFetchSuccess -> {
                if (event.isOnboarding) {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
                } else {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_viewPagerFragment)
                }
            }
        }
    }

    private fun getCachedOnboarding() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getCachedOnboarding().collect { event ->
                    updateViewOnEvent(event)
                }
            }
        }
    }
}