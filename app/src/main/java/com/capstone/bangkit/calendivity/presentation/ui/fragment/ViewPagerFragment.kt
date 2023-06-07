package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.bangkit.calendivity.databinding.FragmentViewPagerBinding
import com.capstone.bangkit.calendivity.presentation.ui.adapter.ViewPagerAdapter

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf(
            Pageone(),
            Pagetwo(),
            Pagethree()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        _binding?.apply {
            viewPager.adapter = adapter
            onBoardingIndicator.setViewPager2(viewPager)
        }

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}