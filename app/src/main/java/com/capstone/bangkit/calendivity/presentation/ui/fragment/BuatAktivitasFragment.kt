package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.data.models.AddUserActivityDataModel
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.databinding.FragmentBuatAktivitasBinding
import com.capstone.bangkit.calendivity.presentation.di.AddUserActViewModel
import com.capstone.bangkit.calendivity.presentation.di.RefreshTokenViewModel
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.shuhart.stepview.StepView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BuatAktivitasFragment : Fragment() {
    private var _binding: FragmentBuatAktivitasBinding? = null
    private val binding get() = _binding!!
    private var calendarStart = Calendar.getInstance()
    private var calendarEnd = Calendar.getInstance()
    private val viewModelAddUser by viewModels<AddUserActViewModel>()
    private val viewModelRefreshToken by viewModels<RefreshTokenViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBuatAktivitasBinding.inflate(layoutInflater, container, false)

        val a = activity?.findViewById<ConstraintLayout>(R.id.coba)

        val stepView = activity?.findViewById<StepView>(R.id.step_view)


        _binding?.apply {
            edtAtkvitias.setText(arguments?.getString("BUAT_AKTIVITAS"))

            edtTanggal.showSoftInputOnFocus = false

            edtStart.showSoftInputOnFocus = false

            edtEnd.showSoftInputOnFocus = false
        }

        binding.edtTanggal.setOnFocusChangeListener { _, b ->
            if (b) {
                Timber.d("cek")
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Pilih Tanggal")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()

                datePicker.show(requireActivity().supportFragmentManager, "")

                datePicker.addOnPositiveButtonClickListener {
                    val date = Date(it)
                    val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
                    val formattedDate = format.format(date)
                    binding.edtTanggal.setText(formattedDate)
                    calendarStart.time = Date(it)
                    calendarEnd.time = Date(it)
                }
            }
        }

        binding.apply {
            edtStart.setOnFocusChangeListener { _, b ->
                if (b) {
                    val picker =
                        MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setHour(12)
                            .setMinute(10)
                            .setTitleText("Waktu Mulai")
                            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                            .build()

                    picker.show(requireActivity().supportFragmentManager, "")

                    picker.addOnPositiveButtonClickListener {
                        edtStart.setText("${picker.hour}:${picker.minute}")
                        calendarStart.set(Calendar.HOUR_OF_DAY, picker.hour)
                        calendarStart.set(Calendar.MINUTE, picker.minute)
                    }
                }
            }

            edtEnd.setOnFocusChangeListener { _, b ->
                if (b) {
                    val picker =
                        MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setHour(12)
                            .setMinute(10)
                            .setTitleText("Waktu Selesai")
                            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                            .build()

                    picker.show(requireActivity().supportFragmentManager, "")

                    picker.addOnPositiveButtonClickListener {
                        edtEnd.setText("${picker.hour}:${picker.minute}")
                        calendarEnd.set(Calendar.HOUR_OF_DAY, picker.hour)
                        calendarEnd.set(Calendar.MINUTE, picker.minute)
                    }
                }
            }


            btnNext.setOnClickListener {
                if (edtAtkvitias.text.toString().isNotEmpty() && edtTanggal.text.toString()
                        .isNotEmpty() && edtStart.text.toString()
                        .isNotEmpty() && edtEnd.text.toString().isNotEmpty()
                ) {
                    viewModelRefreshToken.getRefreshToken(
                        RefreshTokenDataModel(
                            getAccessToken()!!,
                            getRefreshToken()!!
                        )
                    )
                    viewModelRefreshToken.res.observe(viewLifecycleOwner) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                // simpan ac token yang baru
                                setAccessToken(it.data!!.accessToken!!)
                                viewModelAddUser.addUserAct(
                                    "Bearer " + it.data.accessToken!!,
                                    AddUserActivityDataModel(
                                        edtAtkvitias.text.toString(),
                                        edtDeskripsi.text.toString(),
                                        convert(calendarStart), convert(calendarEnd)
                                    )
                                )
                                viewModelAddUser.res.observe(viewLifecycleOwner) { res ->
                                    Utils.showLoading(
                                        binding.progressIndicator,
                                        res.status == Status.LOADING
                                    )
                                    when (res.status) {
                                        Status.SUCCESS -> {
                                            stepView?.go(stepView.currentStep + 1, true)
                                            a?.visibility = View.INVISIBLE
                                            requireActivity().supportFragmentManager.beginTransaction()
                                                .replace(R.id.multi_form, SelesaiFragment())
                                                .addToBackStack(null).commit()
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
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Isi data aktivitas dengan lengkap",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }



        a?.visibility = View.VISIBLE

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    @SuppressLint("SimpleDateFormat")
    fun convert(calendar: Calendar): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return format.format(calendar.time)
    }

    private fun setAccessToken(accessToken: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("akses_token", accessToken)
        editor.apply()
    }
}