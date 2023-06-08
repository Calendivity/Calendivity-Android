package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentPageOneBinding

class Pageone : Fragment() {

    private var _binding: FragmentPageOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPageOneBinding.inflate(layoutInflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        _binding?.btnNext?.setOnClickListener {
            viewPager?.currentItem = 1
        }

        // add callback stack for onboarding
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    _binding?.apply {
                        if (viewPager?.currentItem == 0) {
                            activity?.finish()
                        } else if (viewPager?.currentItem!! <= 2) {
                            viewPager.currentItem -= 1
                        }
                    }
                }
            })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}