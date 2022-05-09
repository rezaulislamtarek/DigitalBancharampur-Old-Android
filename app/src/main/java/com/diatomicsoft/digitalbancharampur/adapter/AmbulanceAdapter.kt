package com.diatomicsoft.digitalbancharampur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.RvAmbulanceBinding
import com.diatomicsoft.digitalbancharampur.databinding.RvPlacesBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.AmbulanceClickListener
import com.diatomicsoft.digitalbancharampur.interfaceutil.ItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Ambulance

class AmbulanceAdapter(val data: List<Ambulance>, val listener: AmbulanceClickListener) :
    RecyclerView.Adapter<AmbulanceAdapter.AmbulanceViewHolder>() {

    inner class AmbulanceViewHolder(val rvAmbulanceBinding: RvAmbulanceBinding) :
        RecyclerView.ViewHolder(rvAmbulanceBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmbulanceViewHolder {
        return AmbulanceViewHolder(
            DataBindingUtil.inflate<RvAmbulanceBinding>(
                LayoutInflater.from(parent.context),
                R.layout.rv_ambulance,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AmbulanceViewHolder, position: Int) {
        holder.rvAmbulanceBinding.ambulance = data[position]
        holder.rvAmbulanceBinding.root.setOnClickListener {
            listener.ambulanceClickListener(it, data[position]);
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}