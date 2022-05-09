package com.diatomicsoft.digitalbancharampur.interfaceutil

import android.view.View
import com.diatomicsoft.digitalbancharampur.model.data.Car

interface CarItemClickListener {
    fun itemClickListener(view: View, item: Car)
}