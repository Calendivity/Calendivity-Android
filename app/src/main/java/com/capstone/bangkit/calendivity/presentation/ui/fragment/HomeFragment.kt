package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.data.models.DataItem
import com.capstone.bangkit.calendivity.data.models.GetListAct
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.models.UpdateAcct
import com.capstone.bangkit.calendivity.databinding.FragmentHomeBinding
import com.capstone.bangkit.calendivity.presentation.di.GetAllUserActViewModel
import com.capstone.bangkit.calendivity.presentation.di.RefreshTokenViewModel
import com.capstone.bangkit.calendivity.presentation.di.UpdateUserActViewmodel
import com.capstone.bangkit.calendivity.presentation.di.UserInfoViewModel
import com.capstone.bangkit.calendivity.presentation.ui.activity.BuatActivity
import com.capstone.bangkit.calendivity.presentation.ui.adapter.GetAllActAdapter
import com.capstone.bangkit.calendivity.presentation.ui.adapter.ListAktivitasAdapter
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModelUserInfo by viewModels<UserInfoViewModel>()
    private val viewModelRefreshToken by viewModels<RefreshTokenViewModel>()
    private val viewModelGetAllAct by viewModels<GetAllUserActViewModel>()
    private val viewModelUpdateAct by viewModels<UpdateUserActViewmodel>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        viewModelRefreshToken.getRefreshToken(
            RefreshTokenDataModel(getAccessToken()!!, getRefreshToken()!!)
        )

        val loading = activity?.findViewById<CircularProgressIndicator>(R.id.progress_indicator)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListAktivitas.layoutManager = layoutManager

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
                                        binding.halamanUtama.visibility = View.VISIBLE
                                        Glide.with(requireActivity()).load(value?.data?.picture).centerCrop()
                                            .into(binding.circleImageView)
                                        binding.materialTextView2.text = value?.data?.name
                                        binding.materialTextView4.text =
                                            value?.data?.level.toString() + " - exp " + value?.data?.exp.toString()
                                    }
                                }
                                Status.LOADING -> {

                                }
                                Status.ERROR -> {

                                }
                            }
                        }

                        //
                        viewModelGetAllAct.getAllUserAct("Bearer " + it.data.accessToken!!)
                        viewModelGetAllAct.res.observe(viewLifecycleOwner) { res ->
                            when (res.status) {
                                Status.SUCCESS -> {
                                    res.data.let { value ->
                                        val nilai = GetListAct(value?.data.filterByValue(false))
                                        var adapter = GetAllActAdapter(nilai)
                                        adapter.setData(nilai)
                                        binding.rvListAktivitas.adapter = adapter
                                        adapter.setOnItemClickCallback(object :
                                            GetAllActAdapter.OnItemClickCallback {
                                            override fun onItemClicked(data: DataItem) {

                                                viewModelUpdateAct.updateUserAct(
                                                    "Bearer " + it.data.accessToken!!,
                                                    data.activityId!!,
                                                    UpdateAcct(
                                                        data.activityName,
                                                        data.description,
                                                        data.startTime,
                                                        true,
                                                        data.endTime
                                                    )
                                                )

                                                viewModelUpdateAct.res.observe(viewLifecycleOwner) { res2 ->
                                                    Utils.showLoading(
                                                        loading!!,
                                                        res2.status == Status.LOADING
                                                    )
                                                    when (res2.status) {
                                                        Status.SUCCESS -> {
                                                            activity?.supportFragmentManager?.beginTransaction()
                                                                ?.replace(
                                                                    R.id.dashboardFragment,
                                                                    HomeFragment()
                                                                )
                                                                ?.addToBackStack(null)
                                                                ?.commit()
                                                        }
                                                        Status.LOADING -> {

                                                        }
                                                        Status.ERROR -> {

                                                        }
                                                    }
                                                }
                                            }

                                        })
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

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(requireActivity(), BuatActivity::class.java))
        }

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


    private fun setAccessToken(accessToken: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("akses_token", accessToken)
        editor.apply()
    }

    fun List<DataItem?>?.filterByValue(value: Boolean) =
        this?.filter { it?.finish == value }
}