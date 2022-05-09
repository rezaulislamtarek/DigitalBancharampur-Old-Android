package com.diatomicsoft.digitalbancharampur.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.data.Places
import com.diatomicsoft.digitalbancharampur.model.repository.HomeRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import java.lang.Exception

class HomeViewModel(val repository: HomeRepository) : ViewModel() {
    var placeList = MutableLiveData<List<Places>>()
    var hospitalList = MutableLiveData<List<Places>>()
    var listener: ResponseListener? = null


    init {
        main{
            listener?.onStarted()
            try {
                placeList.value = repository.getPlacesByPlaceType("place")
                hospitalList.value = repository.getPlacesByPlaceType("hospital")
            }catch (e: Exception){
                listener?.onError(e.message.toString())
            }

        }
    }

    fun getItems() = repository.getItems()
}