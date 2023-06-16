package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentSelesaiBinding
import com.capstone.bangkit.calendivity.presentation.ui.activity.Dashboard
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.shuhart.stepview.StepView

class SelesaiFragment : Fragment() {
    private var _binding: FragmentSelesaiBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelesaiBinding.inflate(layoutInflater, container, false)

        val btnNext = activity?.findViewById<Button>(R.id.btn_next)

        val btnBack = activity?.findViewById<Button>(R.id.btn_back)

        val stepView = activity?.findViewById<StepView>(R.id.step_view)

        btnNext?.setOnClickListener {
            saveIsBiodata()
            startActivity(Intent(requireActivity(), Dashboard::class.java))
        }

        btnBack?.setOnClickListener {
            stepView?.go(stepView.currentStep - 1, true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.multi_form, AktivitasPertamaFragment())
                .addToBackStack(null).commit()
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveIsBiodata() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_Biodata", true)
        editor.apply()
    }

}