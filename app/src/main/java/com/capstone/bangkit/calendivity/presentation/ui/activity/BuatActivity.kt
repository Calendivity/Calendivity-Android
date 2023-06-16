package com.capstone.bangkit.calendivity.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.bangkit.calendivity.data.models.AddUserActivityDataModel
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.databinding.ActivityBuatBinding
import com.capstone.bangkit.calendivity.presentation.di.AddUserActViewModel
import com.capstone.bangkit.calendivity.presentation.di.RefreshTokenViewModel
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class BuatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuatBinding
    private var calendarStart = Calendar.getInstance()
    private var calendarEnd = Calendar.getInstance()
    private val viewModelAddUser by viewModels<AddUserActViewModel>()
    private val viewModelRefreshToken by viewModels<RefreshTokenViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuatBinding.inflate(layoutInflater)

//        binding.edtAtkvitias.setText(intent.getStringExtra("BUAT_AKTIVITAS"))

        binding.edtTanggal.showSoftInputOnFocus = false

        binding.edtStart.showSoftInputOnFocus = false

        binding.edtEnd.showSoftInputOnFocus = false

        binding.edtTanggal.setOnFocusChangeListener { _, b ->
            if (b) {
                Timber.d("cek")
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Pilih Tanggal")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()

                datePicker.show(supportFragmentManager, "")

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
                            .setInputMode(INPUT_MODE_CLOCK)
                            .build()

                    picker.show(supportFragmentManager, "")

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
                            .setInputMode(INPUT_MODE_CLOCK)
                            .build()

                    picker.show(supportFragmentManager, "")

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
                    viewModelRefreshToken.res.observe(this@BuatActivity) {
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
                                viewModelAddUser.res.observe(this@BuatActivity) { res ->
                                    Utils.showLoading(
                                        binding.progressIndicator,
                                        res.status == Status.LOADING
                                    )
                                    when (res.status) {
                                        Status.SUCCESS -> {
                                            startActivity(
                                                Intent(
                                                    this@BuatActivity,
                                                    Dashboard::class.java
                                                )
                                            )
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
                        this@BuatActivity,
                        "Isi data aktivitas dengan lengkap",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        setContentView(binding.root)
    }

    private fun getAccessToken(): String? {
        val sharedPreferences =
            this.getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("akses_token", "kosong")
    }

    private fun getRefreshToken(): String? {
        val sharedPreferences =
            this.getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("refresh_token", "kosong")
    }

    @SuppressLint("SimpleDateFormat")
    fun convert(calendar: Calendar): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return format.format(calendar.time)
    }

    private fun setAccessToken(accessToken: String) {
        val sharedPreferences =
            this.getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("akses_token", accessToken)
        editor.apply()
    }
}