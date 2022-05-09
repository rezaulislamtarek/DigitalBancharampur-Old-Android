package com.diatomicsoft.digitalbancharampur.ui.ambulance

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diatomicsoft.digitalbancharampur.BaseViewModel
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.Ambulance
import com.diatomicsoft.digitalbancharampur.model.data.User
import com.diatomicsoft.digitalbancharampur.model.repository.AmbulanceRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import java.io.File

class AmbulanceViewModel(val repository: AmbulanceRepository) : BaseViewModel() {
    var ambulanceListener: ResponseListener? = null
    var ambulanceList = MutableLiveData<List<Ambulance>>()
    var ambulance = Ambulance()
    var imageUrl = MutableLiveData<Uri>()
    var file = MutableLiveData<File>()
    var user = MutableLiveData<User>()

    init {
        ambulanceListener?.onStarted()
        main {
           try {
               ambulanceList.value = repository.getAllAmbulance()
           }catch (e : Exception){
               ambulanceListener?.onError(e.message.toString())
           }
        }
    }

    suspend fun addAmbulance() {
        if (file.value == null) {
            ambulanceListener?.onError("Please attach the ambulance photo.")
            return
        }
        if(ambulance.driverName.trim().isNullOrEmpty()
            || ambulance.mobile.trim().isNullOrEmpty()
            || ambulance.address.trim().isNullOrEmpty()
            || ambulance.ambulanceRegNo.trim().isNullOrEmpty()
        ) {
            ambulanceListener?.onError("Please fill up all with valid information.")
            return
        }
        try {
            ambulanceListener?.onStarted()
            ambulance.contributorId = repository.getContributorId()
            var res = repository.addaAmbulance(ambulance, file.value!!)
            ambulanceListener?.onSuccess(res)
        }catch (e : Exception){
            ambulanceListener?.onError(e.message.toString())
        }

    }

    private fun print(msg: String) {
        Log.d("ambulance", msg)
    }
    fun getUser(id: Int) {
        Coroutines.main {
            var res: User;
            try {
                res = repository.getUserById(id)
                user.value = res
                Log.d("user success", res.image)
            } catch (e: Exception) {
                Log.d("user error", e.message.toString())
            }
        }
    }

}