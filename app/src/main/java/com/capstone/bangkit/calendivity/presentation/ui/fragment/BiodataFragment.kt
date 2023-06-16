package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.models.UpdateUserInfoDataModel
import com.capstone.bangkit.calendivity.databinding.FragmentBiodataBinding
import com.capstone.bangkit.calendivity.presentation.di.RefreshTokenViewModel
import com.capstone.bangkit.calendivity.presentation.di.UpdateUserInfoViewModel
import com.capstone.bangkit.calendivity.presentation.di.UserInfoViewModel
import com.capstone.bangkit.calendivity.presentation.utils.IOnBackPressed
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shuhart.stepview.StepView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BiodataFragment : Fragment(), IOnBackPressed {
    private var _binding: FragmentBiodataBinding? = null
    private val binding get() = _binding!!
    private val viewModelUserInfo by viewModels<UserInfoViewModel>()
    private val viewModelRefreshToken by viewModels<RefreshTokenViewModel>()
    private val viewModelUpdateUserInfo by viewModels<UpdateUserInfoViewModel>()
    var valueItem = mutableMapOf<String, Int>()

    @SuppressLint("ResourceType", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBiodataBinding.inflate(layoutInflater, container, false)

        // btn next click
        val btnNext = activity?.findViewById<Button>(R.id.btn_next)

        val btnBack = activity?.findViewById<Button>(R.id.btn_back)

        val stepView = activity?.findViewById<StepView>(R.id.step_view)

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
                            viewModelUserInfo.getUserInfo("Bearer " + it.data.accessToken!!)
                            viewModelUserInfo.res.observe(viewLifecycleOwner) { res ->
                                when (res.status) {
                                    Status.SUCCESS -> {
                                        res.data.let { value ->
                                            _binding?.edtNama?.setText(value?.data?.name)
                                        }
                                    }
                                    Status.LOADING -> {

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



        _binding?.apply {
            // TODO : implementasinya masih jelek nanti cari tahu lagi masih ada bug di edittext
            listEmploymentType.setOnItemClickListener { _, _, i, _ ->
                if (i >= 0) {
                    valueItem["employmentType"] = i
                    dropDownListEmploymentType.error = null
                    dropDownListEmploymentType.isEndIconVisible = true
                }
            }

            listCurrentEducation.setOnItemClickListener { _, _, i, _ ->
                if (i >= 0) {
                    valueItem["currentEducation"] = i
                    dropDownListCurrentEducation.error = null
                    dropDownListCurrentEducation.isEndIconVisible = true
                }
            }

            listGender.setOnItemClickListener { _, _, i, _ ->
                if (i >= 0) {
                    valueItem["gender"] = i
                    dropDownListGender.error = null
                    dropDownListGender.isEndIconVisible = true
                }
            }

            listJob.setOnItemClickListener { _, _, i, _ ->
                if (i >= 0) {
                    valueItem["job"] = i
                    dropDownListJob.error = null
                    dropDownListJob.isEndIconVisible = true
                }
            }

            listLastEducation.setOnItemClickListener { _, _, i, _ ->
                if (i >= 0) {
                    valueItem["lastEducation"] = i
                    dropDownListLastEducation.error = null
                    dropDownListLastEducation.isEndIconVisible = true
                }
            }

            edtNama.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    nama.error = null
                }
            }

            edtUmur.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    umur.error = null
                }
            }

            btnBack?.setOnClickListener {
                if (stepView?.currentStep == 0) {
                    activity?.finish()
                } else {
                    stepView?.go(stepView.currentStep - 1, true)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.multi_form, BiodataFragment()).addToBackStack(null).commit()
                }
            }

            btnNext?.setOnClickListener {
                when {
                    edtNama.text.toString().isEmpty() -> {
                        nama.error = resources.getString(R.string.data_kosong)
                        edtNama.requestFocus()
                    }
                    edtUmur.text.toString().isEmpty() -> {
                        umur.error = resources.getString(R.string.data_kosong)
                        edtUmur.requestFocus()
                    }
                    listGender.text.toString().isEmpty() -> {
                        dropDownListGender.isEndIconVisible = false
                        dropDownListGender.error = resources.getString(R.string.data_kosong)
                        listGender.requestFocus()
                    }
                    listCurrentEducation.text.toString().isEmpty() -> {
                        dropDownListCurrentEducation.isEndIconVisible = false
                        dropDownListCurrentEducation.error =
                            resources.getString(R.string.data_kosong)
                        listCurrentEducation.requestFocus()
                    }
                    listLastEducation.text.toString().isEmpty() -> {
                        dropDownListLastEducation.isEndIconVisible = false
                        dropDownListLastEducation.error =
                            resources.getString(R.string.data_kosong)
                        listLastEducation.requestFocus()
                    }
                    listJob.text.toString().isEmpty() -> {
                        dropDownListJob.isEndIconVisible = false
                        dropDownListJob.error = resources.getString(R.string.data_kosong)
                        listJob.requestFocus()
                    }
                    listEmploymentType.text.toString().isEmpty() -> {
                        dropDownListEmploymentType.isEndIconVisible = false
                        dropDownListEmploymentType.error =
                            resources.getString(R.string.data_kosong)
                        listEmploymentType.requestFocus()
                    }
                }
                if (listEmploymentType.text.toString()
                        .isNotEmpty() && listCurrentEducation.text.toString()
                        .isNotEmpty() && listGender.text.toString()
                        .isNotEmpty() && listJob.text.toString().isNotEmpty()
                    && listLastEducation.text.toString().isNotEmpty() && edtNama.text.toString()
                        .isNotEmpty() && edtUmur.text.toString().isNotEmpty()
                ) {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle(resources.getString(R.string.biodata_title))
                        .setMessage(resources.getString(R.string.biodata_message))
                        .setNeutralButton(resources.getString(R.string.btn_tidak)) { _, _ ->
                        }
                        .setPositiveButton(resources.getString(R.string.btn_ya)) { _, _ ->
                            val education =
                                resources.obtainTypedArray(R.array.pendidikan_key)
                                    .getInt(valueItem["currentEducation"]!!, -1).toString()
                            val gender =
                                resources.obtainTypedArray(R.array.jenis_kelamin_key)
                                    .getInt(valueItem["gender"]!!, -1).toString()
                            val lastEducation =
                                resources.obtainTypedArray(R.array.pendidikan_terakhir_key)
                                    .getInt(valueItem["lastEducation"]!!, -1).toString()
                            val employmentType =
                                resources.obtainTypedArray(R.array.jenis_pekerjaan_key)
                                    .getInt(valueItem["employmentType"]!!, -1).toString()
                            val job =
                                resources.obtainTypedArray(R.array.pekerjaan_key)
                                    .getInt(valueItem["job"]!!, -1).toString()

                            viewModelUpdateUserInfo.updateUserInfo(
                                "Bearer " + getAccessToken(),
                                updateUserInfo(
                                    education,
                                    gender,
                                    lastEducation,
                                    employmentType,
                                    edtNama.text.toString(),
                                    job,
                                    edtUmur.text.toString()
                                )
                            )

                            viewModelUpdateUserInfo.res.observe(viewLifecycleOwner) {
                                when (it.status) {
                                    Status.LOADING -> {
                                    }
                                    Status.SUCCESS -> {
                                        stepView?.go(stepView.currentStep + 1, true)
                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(R.id.multi_form, AktivitasPertamaFragment())
                                            .addToBackStack(null).commit()
                                    }
                                    Status.ERROR -> {
                                    }
                                }
                            }
                        }
                        .show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        resources.getString(R.string.validate_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        return binding.root
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

    private fun setAccessToken(accessToken: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("akses_token", accessToken)
        editor.apply()
    }

    private fun updateUserInfo(
        education: String,
        gender: String,
        lastEducation: String,
        employmentType: String,
        name: String,
        job: String,
        age: String
    ): UpdateUserInfoDataModel {
        return UpdateUserInfoDataModel(
            education,
            gender,
            lastEducation,
            employmentType,
            name,
            job,
            age
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed(): Boolean {
//        var validate = false
//        _binding?.apply {
//            if (listEmploymentType.text.toString()
//                    .isNotEmpty() || listCurrentEducation.text.toString()
//                    .isNotEmpty() || listGender.text.toString()
//                    .isNotEmpty() || listJob.text.toString().isNotEmpty()
//                || listLastEducation.text.toString().isNotEmpty() || edtNama.text.toString()
//                    .isNotEmpty() || edtUmur.text.toString().isNotEmpty()
//            ) {
//                validate = true
//            }
//        }
        return true
    }

}