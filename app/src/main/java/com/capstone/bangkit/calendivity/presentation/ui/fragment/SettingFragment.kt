package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.databinding.FragmentSettingBinding
import com.capstone.bangkit.calendivity.presentation.di.RefreshTokenViewModel
import com.capstone.bangkit.calendivity.presentation.di.UserInfoViewModel
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private val viewModelUserInfo by viewModels<UserInfoViewModel>()
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModelRefreshToken by viewModels<RefreshTokenViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        val loading = activity?.findViewById<CircularProgressIndicator>(R.id.progress_indicator)

        viewModelRefreshToken.getRefreshToken(
            RefreshTokenDataModel(
                getAccessToken()!!,
                getRefreshToken()!!
            )
        )
        viewModelRefreshToken.res.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                when (it.status) {
                    Status.SUCCESS -> {
                        // simpan ac token yang baru
                        setAccessToken(it.data!!.accessToken!!)
                        viewModelUserInfo.getUserInfo("Bearer " + it.data.accessToken!!)
                        viewModelUserInfo.res.observe(viewLifecycleOwner) { res ->
                            Utils.showLoading(
                                loading!!,
                                res.status == Status.LOADING
                            )
                            when (res.status) {
                                Status.SUCCESS -> {
                                    res.data.let { value ->
                                        binding.halamanSetting.visibility = View.VISIBLE

                                        var hasilGender = ""
                                        var hasilLd = ""
                                        var hasilCd = ""
                                        var hasilJob = ""
                                        var hasilType = ""

                                        hasilGender = when (value?.data?.gender?.toInt()) {
                                            1 -> "Pria"
                                            2 -> "Wanita"
                                            else -> ""
                                        }

                                        hasilLd = when (value?.data?.lastEducation?.toInt()) {
                                            31 -> "Kurang dari kelas 1 SD"
                                            32 -> "Kelas 1, 2, 3, atau 4 SD"
                                            33 -> "Kelas 5 atau 6 SD"
                                            34 -> "Kelas 7 atau 8 SMP"
                                            35 -> "Kelas 9 SMP"
                                            36 -> "Kelas 10 SMA"
                                            37 -> "Kelas 11 SMA"
                                            38 -> "Kelas 12 SMA"
                                            39 -> "Lulusan SMA"
                                            40 -> "Mahasiswa belum lulus"
                                            41 -> "Diploma 3 (D3) kejuruan"
                                            42 -> "Diploma 3 (D3) akademik"
                                            43 -> "Sarjana (S1)"
                                            44 -> "Master (S2)"
                                            46 -> "Doktor (S3)"
                                            45 -> "Gelar sekolah profesional (dr, drh, drg, dll.)"
                                            else -> ""
                                        }

                                        hasilCd = when (value?.data?.education?.toInt()) {
                                            -1 -> "Tidak ada pendidikan"
                                            1 -> "SMA"
                                            2 -> "Mahasiswa"
                                            else -> ""
                                        }


                                        hasilJob = when (value?.data?.job?.toInt()) {
                                            -1 -> "Tidak Bekerja"
                                            1 -> "Manajemen"
                                            2 -> "Bisnis dan Keuangan"
                                            3 -> "Komputer dan Matematika"
                                            4 -> "Arsitektur dan Teknik"
                                            5 -> "Ilmu Kehidupan, Fisika, dan Sosial"
                                            6 -> "Masyarakat dan Layanan Sosial"
                                            7 -> "Hukum"
                                            8 -> "Pendidikan dan Perpustakaan"
                                            9 -> "Seni, Desain, Hiburan, Olahraga, dan Media"
                                            10 -> "Praktisi Kesehatan dan Teknis"
                                            11 -> "Pendukung Kesehatan"
                                            12 -> "Layanan Perlindungan"
                                            13 -> "Persiapan dan Pelayanan Makanan"
                                            14 -> "Kebersihan dan Pemeliharaan Bangunan dan Taman"
                                            15 -> "Perawat Pribadi dan Layanan"
                                            16 -> "Penjualan"
                                            17 -> "Kantor dan Dukungan Administrasi"
                                            18 -> "Pertanian, Perikanan, dan Kehutanan"
                                            19 -> "Konstruksi dan Ekstraksi"
                                            20 -> "Pemasangan, Pemeliharaan, dan Perbaikan"
                                            21 -> "Produksi"
                                            22 -> "Transportasi dan Ekspedisi"
                                            else -> ""
                                        }

                                        hasilType = when (value?.data?.employmentType?.toInt()) {
                                            -1 -> "Tidak Bekerja"
                                            1 -> "Penuh Waktu"
                                            2 -> "Paruh Waktu"
                                            else -> ""
                                        }

                                        Glide.with(requireActivity()).load(value?.data?.picture)
                                            .into(binding.circleImageView)
                                        binding.edtUmur.setText(value?.data?.age!!)
                                        binding.listGender.setText(hasilGender)
                                        binding.listLastEducation.setText(hasilLd)
                                        binding.listCurrentEducation.setText(hasilCd)
                                        binding.listJob.setText(hasilJob)
                                        binding.listEmploymentType.setText(hasilType)
                                        binding.level.text =
                                            "Level " + value?.data?.level.toString()
                                        binding.exp.text = "exp " + value?.data?.exp.toString()

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

        binding.apply {
            edit.setOnClickListener {
                if (edit.text == "Edit") {
                    edit.text = "Save"
                    edtUmur.isEnabled = true
                    listGender.isEnabled = true
                    listLastEducation.isEnabled = true
                    listCurrentEducation.isEnabled = true
                    listJob.isEnabled = true
                    listEmploymentType.isEnabled = true
                } else {
                    edit.text = "Edit"
                    edtUmur.isEnabled = false
                    listGender.isEnabled = false
                    listLastEducation.isEnabled = false
                    listCurrentEducation.isEnabled = false
                    listJob.isEnabled = false
                    listEmploymentType.isEnabled = false
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