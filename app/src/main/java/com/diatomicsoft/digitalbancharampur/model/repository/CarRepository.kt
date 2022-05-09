package com.diatomicsoft.digitalbancharampur.model.repository

import android.content.Context
import com.diatomicsoft.digitalbancharampur.model.data.Car
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.network.Api
import java.io.File

class CarRepository(private val api: Api,val context: Context) : BaseRepo(context,api) {
    suspend fun getCars(): List<Car> {
        return apiRequestWithList { api.getCars() }
    }

    suspend fun addaCar(car: Car, image: File): CommonResponse {
        var driverName = makeStringPart(car.driverName)
        var ownerName = makeStringPart(car.ownerName)
        var ownerNumber = makeStringPart(car.ownerNumber)
        var driverNumber = makeStringPart(car.driverNumber)
        var address = makeStringPart(car.address)
        var contributorId = makeStringPart(car.contributorId.toString())
        var carRegNo = makeStringPart(car.carRegNo)
        var others = makeStringPart(car.others)
        var carRoute = makeStringPart(car.carRoute)
        var startingTime = makeStringPart(car.startingTime)
        var carModel = makeStringPart(car.carModel)
        var image = makeFilePart(image, "image")

        return apiRequest {
            api.addCar(
                driverName,
                ownerName,
                ownerNumber,
                driverNumber,
                address,
                contributorId,
                carRegNo,
                others,
                carRoute,
                startingTime,
                carModel,
                image,
            )
        }

    }
}