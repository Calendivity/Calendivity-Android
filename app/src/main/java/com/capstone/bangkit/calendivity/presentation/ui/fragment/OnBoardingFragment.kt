package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.content.Context
import android.content.Intent
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
import com.capstone.bangkit.calendivity.presentation.ui.activity.Dashboard
import com.capstone.bangkit.calendivity.presentation.utils.OnboardingEvent
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {
    private val viewModelOnboarding by viewModels<OnboardingViewModel>()
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
    }

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
            is OnboardingEvent.CachedOnboardingFetchSuccess -> {
                // if user already in onboarding page then user go login page otherwise onboarding page
                if (event.isOnboarding) {
                    // if user already login go to multi step form page otherwise login page
                    if (getSharedPreferences()) {
                        if (getSharedPreferences1()) {
                            startActivity(Intent(requireActivity(), Dashboard::class.java))
                        } else {
                            findNavController().navigate(R.id.action_onBoardingFragment_to_multiStepForm)
                        }
                    } else {
                        findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
                    }
                } else {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_viewPagerFragment)
                }
            }
        }
    }

    private fun getCachedOnboarding() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModelOnboarding.getCachedOnboarding().collect { event ->
                    updateViewOnEvent(event)
                }
            }
        }
    }

    private fun getSharedPreferences(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLogin", false)
    }

    private fun getSharedPreferences1(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_Biodata", false)
    }
}