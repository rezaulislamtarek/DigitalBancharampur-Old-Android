package com.diatomicsoft.digitalbancharampur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.RvCarBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.CarItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Car

class CarAdapter(val data: List<Car>, val listener: CarItemClickListener) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    inner class CarViewHolder(val binding: RvCarBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = DataBindingUtil.inflate<RvCarBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_car,
            parent, false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.binding.car = data[position]
        holder.binding.root.setOnClickListener {
            listener.itemClickListener(it,data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}