package com.diatomicsoft.digitalbancharampur

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

   fun dialToPhone(num: String, activity: MainActivity){
       try {
           val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$num"))
           activity!!.startActivity(intent)
           Log.d("phone call", "Calling form try...")
       } catch (e: Exception) {
           Log.d("phone error", e.message.toString())
       }
   }
}