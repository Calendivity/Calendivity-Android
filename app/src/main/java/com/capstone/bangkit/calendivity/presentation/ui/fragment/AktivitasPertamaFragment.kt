package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.databinding.FragmentAktivitasPertamaBinding
import com.capstone.bangkit.calendivity.presentation.di.ListRecomendationViewModel
import com.capstone.bangkit.calendivity.presentation.di.RefreshTokenViewModel
import com.capstone.bangkit.calendivity.presentation.ui.adapter.ListAktivitasAdapter
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.shuhart.stepview.StepView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AktivitasPertamaFragment : Fragment() {
    private var _binding: FragmentAktivitasPertamaBinding? = null
    private val binding get() = _binding!!
    private val viewModelListRecomendation by viewModels<ListRecomendationViewModel>()
    private val viewModelRefreshToken by viewModels<RefreshTokenViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAktivitasPertamaBinding.inflate(layoutInflater, container, false)

        val layoutManager = LinearLayoutManager(requireActivity())

        // btn next click
        val btnNext = activity?.findViewById<Button>(R.id.btn_next)

        val btnBack = activity?.findViewById<Button>(R.id.btn_back)

        val stepView = activity?.findViewById<StepView>(R.id.step_view)

        btnNext?.setOnClickListener {
            stepView?.go(stepView.currentStep + 1, true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.multi_form, SelesaiFragment())
                .addToBackStack(null).commit()
        }

        btnBack?.setOnClickListener {
            stepView?.go(stepView.currentStep - 1, true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.multi_form, BiodataFragment())
                .addToBackStack(null).commit()
        }

        binding.rvListAktivitas.layoutManager = layoutManager

        viewModelRefreshToken.getRefreshToken(
            RefreshTokenDataModel(getAccessToken()!!, getRefreshToken()!!)
        )

        viewModelRefreshToken.res.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            // simpan ac token yang baru
                            setAccessToken(it.data!!.accessToken!!)
                            viewModelListRecomendation.getListRecomendation(
                                "Bearer " + it.data.accessToken!!,
                                "2023-06-08T08:00:00.000Z",
                                "2023-06-08T12:00:00.000Z"
                            )
                            viewModelListRecomendation.res.observe(viewLifecycleOwner) { res ->
                                Utils.showLoading(
                                    binding.progressIndicator,
                                    res.status == Status.LOADING
                                )
                                when (res.status) {
                                    Status.LOADING -> {

                                    }
                                    Status.SUCCESS -> {
                                        Timber.d("Cek $res")
                                        val adapter = ListAktivitasAdapter(res.data!!)

                                        binding.rvListAktivitas.adapter = adapter

                                        adapter.setOnItemClickCallback(object :
                                            ListAktivitasAdapter.OnItemClickCallback {
                                            override fun onItemClicked(data: String) {
                                                val bundle = Bundle()
                                                bundle.putString("BUAT_AKTIVITAS", data)
                                                val buatAct = BuatAktivitasFragment()
                                                buatAct.arguments = bundle
                                                requireActivity().supportFragmentManager.beginTransaction()
                                                    .replace(R.id.coba, buatAct)
                                                    .addToBackStack(null).commit()
                                            }

                                        })

                                    }
                                    Status.ERROR -> {

                                    }
                                }
                            }
                        }
                        Status.LOADING -> {

                        }
                        Status.ERROR -> {

                        }
                    }
                }

            }
        }

        return binding.root
    }

    private fun setAccessToken(accessToken: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("akses_token", accessToken)
        editor.apply()
    }

    private fun getAccessToken(): String? {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("akses_token", "kosong")
    }

    private fun getRefreshToken(): String? {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("refresh_token", "kosong")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}