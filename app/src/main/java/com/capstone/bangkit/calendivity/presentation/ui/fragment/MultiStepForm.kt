package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentMultiStepFormBinding
import com.google.android.material.transition.MaterialSharedAxis

class MultiStepForm : Fragment() {
    private var _binding: FragmentMultiStepFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.multi_form, BiodataFragment()).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMultiStepFormBinding.inflate(layoutInflater, container, false)

        // change status bar color to white again
        val window: Window = activity?.window!!
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.md_theme_light_background)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}