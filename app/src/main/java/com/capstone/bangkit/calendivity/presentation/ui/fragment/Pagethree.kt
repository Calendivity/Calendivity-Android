package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentPagethreeBinding
import com.capstone.bangkit.calendivity.presentation.di.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Pagethree : Fragment() {
    private val viewModel by viewModels<OnboardingViewModel>()
    private var _binding: FragmentPagethreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPagethreeBinding.inflate(inflater, container, false)

        _binding?.btnStarted?.setOnClickListener {
            // save user preferences data to local
            cacheOnboarding(true)

            // remove callback stack when user in login page
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        remove()
                        activity?.finish()
                    }
                })
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun cacheOnboarding(
        isOnboarding: Boolean
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.cacheOnboarding(
                    isOnboarding = isOnboarding
                ).collect { }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}