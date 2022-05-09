package com.diatomicsoft.digitalbancharampur.model.repository

import android.content.Context
import com.diatomicsoft.digitalbancharampur.model.data.Item
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.SafeApi

class HomeRepository(private val api: Api, val context: Context) : BaseRepo(context,api) {
    suspend fun getPlacesByPlaceType(type: String) = PlaceRepository(api,context).getPlacesByType(type)

    fun getItems(): ArrayList<Item> {
        return arrayListOf(
            Item("Ambulance", R.drawable.ambulance, "ambulance"),
            Item("Car", R.drawable.car, "car"),
            Item("Bank", R.drawable.bank, "bank"),
            Item("Police", R.drawable.police, "police"),
            Item("Courier", R.drawable.courier, "cs"),
            Item("Education", R.drawable.ic_baseline_school_24, "education"),
            Item("Fire Service", R.drawable.fireservice, "fs"),
            Item("Blood Bank", R.drawable.blood, "Blood Bank"),
        )
    }
}