package com.diatomicsoft.digitalbancharampur.ui.place

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diatomicsoft.digitalbancharampur.BaseViewModel
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.Hints
import com.diatomicsoft.digitalbancharampur.model.data.Places
import com.diatomicsoft.digitalbancharampur.model.data.User
import com.diatomicsoft.digitalbancharampur.model.repository.PlaceRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import java.io.File

class PlaceViewModel(val repository: PlaceRepository, val type: String) : BaseViewModel() {
    var listener: ResponseListener? = null
    var place = Places()
    var imageUri = MutableLiveData<Uri>()
    var file = MutableLiveData<File>()
    var user = MutableLiveData<User>()
    var placeList = MutableLiveData<List<Places>>()

    var hints = Hints(
        "Please enter $type name",
        "Please enter $type address",
        "Please enter $type contact number",
        "Please enter $type details",
        "Please attach a $type photo"
    )


    init {
        listener?.onStarted()

        main {
            try {
                placeList.value = repository.getPlacesByType(type)
            } catch (e: Exception) {
                listener?.onError(e.message.toString())
            }
        }
    }

    fun addPlace(view: View) {
        listener?.onStarted()
        if (file.value == null) {
            listener?.onError("Please attach the $type photo.")
            return
        }
        if (place.title.isNullOrEmpty() ||
            place.address.isNullOrEmpty() ||
            place.mobile.isNullOrEmpty() ||
            place.details.isNullOrEmpty()
        ) {
            listener?.onError("Please fill up all with valid information.")
            return
        }

        main {
            try {
                place.contributorId = repository.getContributorId()
                place.type = type
                val res = repository.addPlace(place, file.value!!)
                listener?.onSuccess(res)
            } catch (e: Exception) {
                listener?.onError(e.message.toString())
            }
        }
    }

    fun getUser(id: Int) {
        main {
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