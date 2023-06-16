package com.capstone.bangkit.calendivity.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.bangkit.calendivity.data.models.DataItem
import com.capstone.bangkit.calendivity.data.models.GetListAct
import com.capstone.bangkit.calendivity.databinding.ListAktivitasHariIniBinding
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class GetAllActAdapter(private var getAllAct: GetListAct) :
    RecyclerView.Adapter<GetAllActAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListAktivitasHariIniBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(getAllAct: GetListAct) {
        this.getAllAct = getAllAct
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = getAllAct.data?.size!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListAktivitasHariIniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val array = getAllAct.data?.get(position)

            binding.aktivitasKamu.text = array?.activityName

            binding.time.text =
                "${convertTanggal(array?.startTime!!)}, ${convertTime(array.startTime)}-${
                    convertTime(array.endTime!!)
                }"
            binding.jumlahBintang.text = array.difficulty.toString()

            binding.exp.text = "exp ${array.exp}"

            binding.cek.setOnCheckedChangeListener { _, b ->
                if (b) {
                    Timber.d("Checked ${array.activityId}")
                    getAllAct.data?.get(holder.adapterPosition)?.let { nilai ->
                        onItemClickCallback.onItemClicked(nilai)
                    }
                } else {
                    Timber.d("Unchecked")
                }
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTanggal(value: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("id", "ID"))

        try {
            val date = format.parse(value)

            val formatLagi = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")).format(date)

            return formatLagi
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertTime(value: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("id", "ID"))

        try {
            val date = format.parse(value)

            val formatLagi = SimpleDateFormat("HH:mm", Locale("id", "ID")).format(date)

            return formatLagi
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}