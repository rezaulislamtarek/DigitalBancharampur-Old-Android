package com.diatomicsoft.digitalbancharampur.interfaceutil

import android.view.View
import com.diatomicsoft.digitalbancharampur.model.data.Ambulance
import com.diatomicsoft.digitalbancharampur.model.data.Places

interface AmbulanceClickListener {
    fun ambulanceClickListener(view: View, item: Ambulance)
}