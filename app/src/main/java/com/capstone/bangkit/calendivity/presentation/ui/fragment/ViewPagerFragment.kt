package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentViewPagerBinding
import com.capstone.bangkit.calendivity.presentation.ui.adapter.ViewPagerAdapter
import com.capstone.bangkit.calendivity.presentation.utils.Utils

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        // change status bar color
        if (!getSharedPreferences()) {
            val window: Window = activity?.window!!
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.md_theme_light_tertiaryContainer)
        }

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

    private fun getSharedPreferences(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLogin", false)
    }
}