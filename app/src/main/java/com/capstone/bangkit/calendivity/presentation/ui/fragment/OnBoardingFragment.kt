package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
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

        // change status bar color
        val window: Window = activity?.window!!
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.md_theme_light_tertiaryContainer)

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
                // if user already in onboarding page then user go login page otherwise onboarding page
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