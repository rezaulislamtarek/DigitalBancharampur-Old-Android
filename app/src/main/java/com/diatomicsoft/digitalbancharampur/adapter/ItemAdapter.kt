package com.diatomicsoft.digitalbancharampur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.RvItemBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Item

class ItemAdapter(val data: ArrayList<Item>, val listener: ItemClickListener) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val rvItemBinding: RvItemBinding) :
        RecyclerView.ViewHolder(rvItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<RvItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_item,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.rvItemBinding.item = data[position]
        holder.rvItemBinding.root.setOnClickListener {
            listener.itemClickListener(it, data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}