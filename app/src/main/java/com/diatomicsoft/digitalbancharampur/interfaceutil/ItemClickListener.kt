package com.diatomicsoft.digitalbancharampur.interfaceutil
import android.view.View
import com.diatomicsoft.digitalbancharampur.model.data.Item
import com.diatomicsoft.digitalbancharampur.model.data.Places

interface ItemClickListener {
     fun itemClickListener(view: View, item: Item)
      fun placeClickListener(view: View, item: Places)
}