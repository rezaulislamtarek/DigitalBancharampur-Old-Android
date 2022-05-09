package com.diatomicsoft.digitalbancharampur.adapter

import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.RvPlaceHorizontalBinding
import com.diatomicsoft.digitalbancharampur.databinding.RvPlacesBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Places

class PlaceAdapterHorizontal(val data: List<Places>, val flag: Int, val listener: ItemClickListener) :
    RecyclerView.Adapter<PlaceAdapterHorizontal.PlaceViewHolder>() {

    inner class PlaceViewHolder(val rvPlacesBinding: RvPlaceHorizontalBinding) :
        RecyclerView.ViewHolder(rvPlacesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        var binding =  DataBindingUtil.inflate<RvPlaceHorizontalBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_place_horizontal,
            parent,
            false
        )
        binding.flag = flag
        return PlaceViewHolder(
           binding
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.rvPlacesBinding.place = data[position]
        holder.rvPlacesBinding.root.setOnClickListener {
            listener.placeClickListener(it,data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}