package com.capstone.bangkit.calendivity.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.bangkit.calendivity.data.models.ListRecomendationDataModel
import com.capstone.bangkit.calendivity.databinding.ListAktivitasBinding

class ListAktivitasAdapter(private val listAktivitas: ListRecomendationDataModel) :
    RecyclerView.Adapter<ListAktivitasAdapter.ViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(val binding: ListAktivitasBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listAktivitas.data?.size!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListAktivitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val a = listAktivitas.data?.get(position)
            binding.recomendation.text = a
            itemView.setOnClickListener {
                listAktivitas.data?.get(holder.adapterPosition)
                    ?.let { it1 -> onItemClickCallback.onItemClicked(it1) }
            }
        }
    }

}