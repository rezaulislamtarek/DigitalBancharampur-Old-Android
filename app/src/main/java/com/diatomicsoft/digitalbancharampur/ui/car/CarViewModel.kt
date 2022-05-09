package com.diatomicsoft.digitalbancharampur.ui.car

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.digitalbancharampur.BaseViewModel
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.Car
import com.diatomicsoft.digitalbancharampur.model.data.User
import com.diatomicsoft.digitalbancharampur.model.repository.CarRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines
import kotlinx.coroutines.launch
import java.io.File

class CarViewModel(val repository: CarRepository) : BaseViewModel() {
    var listener: ResponseListener? = null
    var carList = MutableLiveData<List<Car>>()
    var file = MutableLiveData<File>()
    var car = Car()
    var user = MutableLiveData<User>()

    init {
        listener?.onStarted()
        viewModelScope.launch {
            try {
                carList.value = repository.getCars()
            } catch (e: Exception) {
                listener?.onError(e.message.toString())
            }
        }
    }

    suspend fun addCar() {
        if (file.value == null) {
            listener?.onError("Please attach the car photo.")
            return
        }
        if (car.carModel.trim().isNullOrEmpty() ||
            car.carRegNo.trim().isNullOrEmpty() ||
            car.carRoute.trim().isNullOrEmpty() ||
            car.address.trim().isNullOrEmpty() ||
            car.driverName.trim().isNullOrEmpty() ||
            car.driverNumber.trim().isNullOrEmpty() ||
            car.ownerName.trim().isNullOrEmpty() ||
            car.ownerName.trim().isNullOrEmpty() ||
            car.startingTime.trim().isNullOrEmpty() ||
            car.others.trim().isNullOrEmpty()
        ) {
            listener?.onError("Please fill up with all valid information")
            return
        }
        car.contributorId = repository.getContributorId()
        listener?.onStarted()
        try {
            val res = repository.addaCar(car, file.value!!)
            listener?.onSuccess(res)
        } catch (e: Exception) {
            listener?.onError(e.message.toString())
        }

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